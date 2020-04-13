package de.marinek.propertymanager.repository;

import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.accounting.BookingAccount;

public interface BookingAccountRepository extends CrudRepository<BookingAccount, Long>{

}
