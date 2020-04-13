package de.marinek.propertymanager.repository;

import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.plan.BudgetPlanDTO;

public interface BusinessPlanRepository extends  CrudRepository<BudgetPlanDTO, Long> {

}
