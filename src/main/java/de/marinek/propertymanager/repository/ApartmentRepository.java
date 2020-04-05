package de.marinek.propertymanager.repository;

import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.property.Apartment;

public interface ApartmentRepository extends CrudRepository<Apartment, Long>{

}
