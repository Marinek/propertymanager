package de.marinek.propertymanager.domain.plan;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

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
public class BudgetPlanDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PeriodBudgetId assocId = new PeriodBudgetId();
	
	@Column
	@Min(value = 0, message = "Das Budget darf nicht 0 oder kleiner als 0 sein.")
	private Double budget = 0.0;
	
	@Column(length = 512)
	private String note;
	
	@Column
	private String externReference;
	
	@ManyToOne
	@MapsId("partnerId")
	private PartnerDTO partner;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("budgetId")
	private BookingAccount bookingAccount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("periodId")
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
	
	public void setPeriode(PeriodDTO periode) {
		if(periode != null) {
			assocId.setPeriodId(periode.getId());
		}
		this.periode = periode;
	}
	
	public void setBookingAccount(BookingAccount bookingAccount) {
		if(bookingAccount != null) {
			assocId.setBudgetId(bookingAccount.getId());
		}
		this.bookingAccount = bookingAccount;
	}
	
	public void setPartner(PartnerDTO partner) {
		if(partner != null) {
			assocId.setPartnerId(partner.getId());
		}
		this.partner = partner;
	}
		
}
