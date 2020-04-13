package de.marinek.propertymanager.repository;

import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.account.AccountDTO;
import nl.garvelink.iban.IBAN;

public interface AccountRepository extends CrudRepository<AccountDTO, Long> {

	public AccountDTO findByiban(IBAN iban);
}
