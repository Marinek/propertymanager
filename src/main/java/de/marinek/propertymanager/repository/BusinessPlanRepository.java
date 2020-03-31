package de.marinek.propertymanager.repository;

import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.accounting.PeriodDTO;

public interface BusinessPlanRepository extends CrudRepository<PeriodDTO, Long> {

}
