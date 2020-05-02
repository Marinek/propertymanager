package de.marinek.propertymanager.domain.plan;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.marinek.propertymanager.repository.BusinessPlanRepository;

@Component
public class UniqueIDValidator implements ConstraintValidator<UniqueId, PeriodBudgetId> {

	@Autowired
	private BusinessPlanRepository repo;

	@Override
	public boolean isValid(PeriodBudgetId value, ConstraintValidatorContext context) {
		return value != null && repo.existsById(value);
	}
}
