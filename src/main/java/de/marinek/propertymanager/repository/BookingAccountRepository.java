package de.marinek.propertymanager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.marinek.propertymanager.domain.accounting.BookingAccount;
import de.marinek.propertymanager.domain.plan.PeriodDTO;

public interface BookingAccountRepository extends CrudRepository<BookingAccount, Long>{
	
	@Query(value = "SELECT ba FROM BookingAccount ba where not exists (select 'found' from BudgetPlanDTO bp where bp.periode = :period and bp.bookingAccount = ba.id)")
	public Iterable<BookingAccount> findByBudgetsPlansNot(PeriodDTO period);
	
}
