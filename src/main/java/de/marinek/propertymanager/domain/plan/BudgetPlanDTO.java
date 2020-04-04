package de.marinek.propertymanager.domain.plan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import de.marinek.propertymanager.domain.accounting.BookingAccount;
import de.marinek.propertymanager.domain.accounting.PartnerDTO;
import de.marinek.propertymanager.domain.accounting.PeriodDTO;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "budget_plan")
@Getter
@Setter
public class BudgetPlanDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PeriodBudgetId assocId = new PeriodBudgetId();
	
	@Column
	private Double budget;
	
	@Column(length = 512)
	private String note;
	
	@Column
	private String externReference;
	
	@ManyToOne
	private PartnerDTO partner;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("budgetId")
	private BookingAccount bookingAccount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("periodId")
	private PeriodDTO periode;
	
}
