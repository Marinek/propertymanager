package de.marinek.propertymanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.marinek.propertymanager.domain.plan.ApartmentPeriod;

@Repository
public interface AppartmentPeriodeRepository extends CrudRepository<ApartmentPeriod, Long> {

}
