package de.marinek.propertymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.marinek.propertymanager.domain.plan.PeriodDTO;

public interface BusinessPeriodRepository extends JpaRepository<PeriodDTO, Long> {

	public PeriodDTO findPeriodByYear(Integer year);
	
}
