package de.marinek.propertymanager.domain.plan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class PeriodBudgetId implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@Column(name = "period_id")
    private Long periodId;
 
    @Column(name = "budget_id")
    private Long budgetId;
 
}