package de.marinek.propertymanager.components;

import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import de.marinek.propertymanager.domain.account.AccountDTO;
import de.marinek.propertymanager.domain.account.TransactionDTO;
import de.marinek.propertymanager.domain.accounting.AccountType;
import de.marinek.propertymanager.domain.accounting.BookingAccount;
import de.marinek.propertymanager.domain.partner.CreditorDTO;
import de.marinek.propertymanager.domain.plan.BudgetPlanDTO;
import de.marinek.propertymanager.domain.plan.PeriodDTO;
import de.marinek.propertymanager.repository.AccountRepository;
import de.marinek.propertymanager.repository.BookingAccountRepository;
import de.marinek.propertymanager.repository.BusinessPeriodRepository;
import de.marinek.propertymanager.repository.BusinessPlanRepository;
import de.marinek.propertymanager.repository.CreditorRepository;
import de.marinek.propertymanager.repository.TransactionRepository;
import nl.garvelink.iban.IBAN;

@Component
public class TransactionImportComponent {

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private BookingAccountRepository bookingAccountRepo;
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@Autowired
	private CreditorRepository partnerRepo;
	
	@Autowired
	private BusinessPlanRepository businessplanRepo;
	
	@Autowired
	private BusinessPeriodRepository businessPeriodRepo;
	
	private Logger logger = LoggerFactory.getLogger(TransactionImportComponent.class);

	private BookingAccount getDefaultBookingAccount() {
		List<BookingAccount> defaultBookingAccounts = bookingAccountRepo.findByType(AccountType.DEFAULT);
		
		if(defaultBookingAccounts.size() == 0) {
			BookingAccount defaultBookingAccount = new BookingAccount();
			
			defaultBookingAccount.setType(AccountType.DEFAULT);
			defaultBookingAccount.setName("Pseudokonto");
			
			return bookingAccountRepo.save(defaultBookingAccount);
		} else {
			return defaultBookingAccounts.get(0);
		}
	}

	public List<TransactionDTO> loadTransactions(MultipartFile file2) {
	    try {
	        
	        CSVReader csvReader =
	        		   new CSVReaderBuilder(new InputStreamReader(file2.getInputStream()))
	        		     .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
	        		     .build();
	        
	        // create csv bean reader
            CsvToBean<TransactionDTO> csvToBean = new CsvToBeanBuilder<TransactionDTO>(csvReader)
                    .withType(TransactionDTO.class)
                    .withSeparator(';')
                    .withQuoteChar('"')
                    .withIgnoreQuotations(true)
                    .withStrictQuotes(true)
                    .build();
            
	        List<TransactionDTO> newlyImportedTransactions = csvToBean.parse();
	        
	        for(TransactionDTO transaction : newlyImportedTransactions) {
	        	logger.info("Start import: " + transaction);
	        	if(transaction.getValue() == 0) {
	        		// Skipp empty Transactions.
	        		continue;
	        	}
	        	
	        	transaction.setUsage(transaction.getUsage().replace('+', ' '));
	        	
	        	
	        	AccountDTO account = resolveAccount(transaction.getAccountIBAN());

	        	if(account != null) {
	        		transaction.setAccount(account);
	        	}
	        	
	        	CreditorDTO creditor = createCreditor(transaction);
	        	
	        	PeriodDTO period = createPeriod(transaction);
	        	
	        	BudgetPlanDTO findByPeriodAndCreditor = createBudgetPlan(creditor, period, transaction);
	        	
	        	if(findByPeriodAndCreditor != null) {
	        		logger.info("--- Transaction auto found Budget!");
	        		transaction.setBudgetPlan(findByPeriodAndCreditor);
	        	}
	        }
	        
	        transactionRepo.saveAll(newlyImportedTransactions);
	        
	        return newlyImportedTransactions;
	        
	    } catch (Exception e) {
	        logger.error("Error occurred while loading object list from file " + file2, e);
	        return Collections.emptyList();
	    }
	}
	
	private BudgetPlanDTO createBudgetPlan(CreditorDTO creditor, PeriodDTO period, TransactionDTO transaction) {
		logger.info("Resolving BudgetPlan...");
		if(creditor == null) {
			logger.info("No Creditor found. Can't resolve BudgetPlan");
			return null;
		}
		
		BudgetPlanDTO findByPeriodAndCreditor = businessplanRepo.findByPeriodAndCreditor(period, creditor);

		if(findByPeriodAndCreditor != null) {
			logger.info("Found existing BudgetPlan: " + findByPeriodAndCreditor);
			
		} else {
			findByPeriodAndCreditor = new BudgetPlanDTO();
			findByPeriodAndCreditor.setPartner(creditor);
			findByPeriodAndCreditor.setNote(transaction.getUsage());
			findByPeriodAndCreditor.setBookingAccount(getDefaultBookingAccount());
			findByPeriodAndCreditor.setPeriode(period);
			
			logger.info("Created new BudgetPlan: " + findByPeriodAndCreditor);
			businessplanRepo.save(findByPeriodAndCreditor);
		}
				
		return findByPeriodAndCreditor;
	}

	private PeriodDTO createPeriod(TransactionDTO transaction) {
		logger.info("Resolving Period...");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(transaction.getDate());
		int year = calendar.get(Calendar.YEAR);
		
		if(year < 2000) {
			year+= 2000;
		}
		
		PeriodDTO findPeriodByYear = businessPeriodRepo.findPeriodByYear(year);
		
		if(findPeriodByYear == null) {
			findPeriodByYear = new PeriodDTO();
			findPeriodByYear.setYear(year);
			
			businessPeriodRepo.save(findPeriodByYear);
		}
		
		logger.info("Resolved Period: "+ findPeriodByYear);
		return findPeriodByYear;
	}

	private CreditorDTO createCreditor(TransactionDTO transaction) {
		logger.info("Resolving creditor...");
		if(transaction.getFromAccountNumber() == null || transaction.getFromAccountNumber().toPlainString().isEmpty()) {
			logger.info("... no IBAN found");
			return null;
		}
		
		CreditorDTO creditor = partnerRepo.findByIban(transaction.getFromAccountNumber());
		
		if(creditor == null) {
			creditor = new CreditorDTO(transaction.getFromAccountNumber(), transaction.getFromName());
			
			partnerRepo.save(creditor);
		}
		
		logger.info("Resolved creditor: " + creditor);
		return creditor;
	}

	@Cacheable("accounts")
	private AccountDTO resolveAccount(IBAN iban) {
		logger.info("Resolving Account: " + iban);
		AccountDTO account = accountRepo.findByiban(iban);
		
		if(account == null) {
			account = new AccountDTO();
			account.setIban(iban);
			account.setName("Automatisch beim Import erstellt.");
			accountRepo.save(account);
		}
		
		logger.info("Resolved Account: " + account);
		return account;
	}
}
