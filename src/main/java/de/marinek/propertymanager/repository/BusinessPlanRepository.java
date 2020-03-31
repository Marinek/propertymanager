package de.marinek.propertymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.marinek.propertymanager.domain.accounting.PeriodDTO;

public interface BusinessPlanRepository extends JpaRepository<PeriodDTO, Long> {

}
