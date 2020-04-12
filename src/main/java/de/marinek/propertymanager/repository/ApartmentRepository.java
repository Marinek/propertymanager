package de.marinek.propertymanager.repository;

import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.property.ApartmentDTO;

public interface ApartmentRepository extends CrudRepository<ApartmentDTO, Long>{

}
