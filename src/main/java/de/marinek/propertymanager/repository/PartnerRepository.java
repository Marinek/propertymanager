package de.marinek.propertymanager.repository;

import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.partner.PartnerDTO;

public interface PartnerRepository extends CrudRepository<PartnerDTO, Long> {

}
