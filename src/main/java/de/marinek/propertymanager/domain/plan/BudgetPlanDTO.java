package de.marinek.propertymanager.domain.plan;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import de.marinek.propertymanager.domain.DataTransfereObject;
import de.marinek.propertymanager.domain.account.TransactionDTO;
import de.marinek.propertymanager.domain.accounting.BookingAccount;
import de.marinek.propertymanager.domain.partner.PartnerDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "budget_plan")
@Getter
@Setter
@ToString
public class BudgetPlanDTO extends DataTransfereObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	@Min(value = 0, message = "Das Budget darf nicht 0 oder kleiner als 0 sein.")
	private Double budget = 0.0;
	
	@Column(length = 512)
	private String note;
	
	@Column
	private String externReference;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private PartnerDTO partner;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private BookingAccount bookingAccount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private PeriodDTO periode;
	
	@OneToMany(mappedBy = "budgetPlan")
	private Set<TransactionDTO> transactions = new HashSet<TransactionDTO>();
	
	public Double getSum() {
		Double sum = 0.0;
		
		for(TransactionDTO transaction : transactions) {
			sum += transaction.getValue();
		}
		
		return sum;
	}
		
}
