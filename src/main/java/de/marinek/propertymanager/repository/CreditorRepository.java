package de.marinek.propertymanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.marinek.propertymanager.domain.partner.CreditorDTO;
import nl.garvelink.iban.IBAN;


@Repository
public interface CreditorRepository extends CrudRepository<CreditorDTO, Long>  {
	
	public CreditorDTO findByIban(IBAN iban);
	
}
