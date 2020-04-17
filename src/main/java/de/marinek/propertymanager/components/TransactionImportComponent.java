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
import de.marinek.propertymanager.domain.partner.CreditorDTO;
import de.marinek.propertymanager.domain.plan.BudgetPlanDTO;
import de.marinek.propertymanager.domain.plan.PeriodDTO;
import de.marinek.propertymanager.repository.AccountRepository;
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
	private TransactionRepository transactionRepo;
	
	@Autowired
	private CreditorRepository partnerRepo;
	
	@Autowired
	private BusinessPlanRepository businessplanRepo;
	
	@Autowired
	private BusinessPeriodRepository businessPeriodRepo;
	
	private Logger logger = LoggerFactory.getLogger(TransactionImportComponent.class);

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
	        	transaction.setUsage(transaction.getUsage().replace('+', ' '));

	        	AccountDTO account = resolveAccount(transaction.getAccountIBAN());

	        	if(account != null) {
	        		transaction.setAccount(account);
	        	}
	        	
	        	CreditorDTO creditor = createCreditor(transaction);
	        	
	        	PeriodDTO period = createPeriod(transaction);
	        	
	        	BudgetPlanDTO findByPeriodAndCreditor = createBudgetPlan(creditor, period, transaction);
	        	
	        	if(findByPeriodAndCreditor != null) {
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
		BudgetPlanDTO findByPeriodAndCreditor = businessplanRepo.findByPeriodAndCreditor(period, creditor);
		
		return findByPeriodAndCreditor;
	}

	private PeriodDTO createPeriod(TransactionDTO transaction) {
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
		
		return findPeriodByYear;
	}

	private CreditorDTO createCreditor(TransactionDTO transaction) {
		if(transaction.getFromAccountNumber() == null || transaction.getFromAccountNumber().toPlainString().isEmpty()) {
			return null;
		}
		
		CreditorDTO creditor = partnerRepo.findByIban(transaction.getFromAccountNumber());
		
		if(creditor == null) {
			creditor = new CreditorDTO(transaction.getFromAccountNumber(), transaction.getFromName());
			
			partnerRepo.save(creditor);
		}
		
		return creditor;
	}

	@Cacheable("accounts")
	private AccountDTO resolveAccount(IBAN iban) {
		AccountDTO account = accountRepo.findByiban(iban);
		
		if(account == null) {
			account = new AccountDTO();
			account.setIban(iban);
			account.setName("Automatisch beim Import erstellt.");
			accountRepo.save(account);
		}
		
		return account;
	}
}
