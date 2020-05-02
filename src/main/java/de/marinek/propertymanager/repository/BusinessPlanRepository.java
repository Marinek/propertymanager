package de.marinek.propertymanager.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.partner.CreditorDTO;
import de.marinek.propertymanager.domain.plan.BudgetPlanDTO;
import de.marinek.propertymanager.domain.plan.PeriodBudgetId;
import de.marinek.propertymanager.domain.plan.PeriodDTO;

public interface BusinessPlanRepository extends  CrudRepository<BudgetPlanDTO, PeriodBudgetId> {

	@Query("select b from BudgetPlanDTO b where b.partner = :creditor and b.periode = :periode ")
	public BudgetPlanDTO findByPeriodAndCreditor(PeriodDTO periode, CreditorDTO creditor);
	
	public List<BudgetPlanDTO> findAll(Example<BudgetPlanDTO> plan);
}
