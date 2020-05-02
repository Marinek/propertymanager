package de.marinek.propertymanager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.property.ApartmentDTO;

public interface ApartmentRepository extends CrudRepository<ApartmentDTO, Long>{

	@Query("SELECT a FROM ApartmentDTO a where not exists (select 1 from ApartmentPeriod where a.id = apartment_id and periode_id = :periode_id)")
	public Iterable<ApartmentDTO> findNotAssociated(Long periode_id);
}
