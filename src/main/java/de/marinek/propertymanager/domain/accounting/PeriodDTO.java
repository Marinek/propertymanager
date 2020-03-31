package de.marinek.propertymanager.domain.accounting;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.marinek.propertymanager.domain.DataTransfereObject;
import de.marinek.propertymanager.domain.plan.BudgetPlanDTO;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "periods")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PeriodDTO extends DataTransfereObject {

	@Column
	private String name;
	
	@Column
	private Date startDate;
	
	@Column
	private Date endDate;

	 @OneToMany(
		        mappedBy = "id",
		        cascade = CascadeType.ALL,
		        orphanRemoval = true
		    )
	private Set<BudgetPlanDTO> bookingAccounts = new HashSet<BudgetPlanDTO>();
	
	 public void addBookingAccount(BookingAccount ba) {
		 BudgetPlanDTO budgetPlan = new BudgetPlanDTO();
		 budgetPlan.setPeriode(this);
		 budgetPlan.setBookingAccount(ba);
		 
		 bookingAccounts.add(budgetPlan);
	 }
}
