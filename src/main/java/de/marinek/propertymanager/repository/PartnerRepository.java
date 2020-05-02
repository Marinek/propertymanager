package de.marinek.propertymanager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.partner.PartnerDTO;

public interface PartnerRepository extends CrudRepository<PartnerDTO, Long> {

	@Query("select u from PartnerDTO u where partnertype = :type")
	public Iterable<PartnerDTO> findByType(String type);
}
