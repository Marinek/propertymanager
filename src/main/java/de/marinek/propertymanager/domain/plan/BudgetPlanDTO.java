package de.marinek.propertymanager.domain.plan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import de.marinek.propertymanager.domain.DataTransfereObject;
import de.marinek.propertymanager.domain.accounting.BookingAccount;
import de.marinek.propertymanager.domain.accounting.PeriodDTO;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "budget_plan")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BudgetPlanDTO extends DataTransfereObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PeriodBudgetId assocId;
	
	@Column
	private Double budget;
	
	@Column(length = 512)
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("budget_id")
	private BookingAccount bookingAccount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("period_id")
	private PeriodDTO periode;
	
	
}
