package de.marinek.propertymanager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.partner.CreditorDTO;
import de.marinek.propertymanager.domain.plan.BudgetPlanDTO;
import de.marinek.propertymanager.domain.plan.PeriodDTO;

public interface BusinessPlanRepository extends  CrudRepository<BudgetPlanDTO, Long> {

	@Query("select b from BudgetPlanDTO b where b.partner = :creditor and b.periode = :periode ")
	public BudgetPlanDTO findByPeriodAndCreditor(PeriodDTO periode, CreditorDTO creditor);
}
