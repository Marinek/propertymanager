package de.marinek.propertymanager.components;

import java.io.InputStreamReader;
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
import de.marinek.propertymanager.repository.AccountRepository;
import de.marinek.propertymanager.repository.TransactionRepository;
import nl.garvelink.iban.IBAN;

@Component
public class TransactionImportComponent {

	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private TransactionRepository transactionRepo;
	
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
	        	AccountDTO account = resolveAccount(transaction.getAccountIBAN());

	        	if(account != null) {
	        		transaction.setAccount(account);
	        	}
	        	
	        	transaction.setUsage(transaction.getUsage().replace('+', ' '));
	        	
	        }
	        
	        transactionRepo.saveAll(newlyImportedTransactions);
	        
	        return newlyImportedTransactions;
	        
	    } catch (Exception e) {
	        logger.error("Error occurred while loading object list from file " + file2, e);
	        return Collections.emptyList();
	    }
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
