package de.marinek.propertymanager.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.accounting.AccountType;
import de.marinek.propertymanager.domain.accounting.BookingAccount;

public interface BookingAccountRepository extends CrudRepository<BookingAccount, Long>{
	
	public List<BookingAccount> findByType(AccountType t);
}
