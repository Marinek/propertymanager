package de.marinek.propertymanager.repository;

import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.account.TransactionDTO;

public interface TransactionRepository extends CrudRepository<TransactionDTO, Long> {

}
