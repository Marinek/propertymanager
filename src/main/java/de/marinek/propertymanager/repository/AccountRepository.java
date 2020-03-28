package de.marinek.propertymanager.repository;

import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.account.AccountDTO;

public interface AccountRepository extends CrudRepository<AccountDTO, String> {

}
