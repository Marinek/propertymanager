package de.marinek.propertymanager.domain.plan;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;

import org.springframework.data.annotation.CreatedDate;

import de.marinek.propertymanager.domain.DataTransfereObject;
import de.marinek.propertymanager.domain.property.ApartmentDTO;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "periods")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PeriodDTO extends DataTransfereObject {

	@Column
	private Integer year;
	
	@Column
	@CreatedDate
	private Date createdAt = new Date();
	
	@OneToMany(
			mappedBy = "periode",
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
			)
	@OrderBy(value = "id asc")
	private Set<BudgetPlanDTO> bookingAccounts = new HashSet<BudgetPlanDTO>();
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "periode")
	private Set<ApartmentPeriod> appartments = new HashSet<ApartmentPeriod>();
	
	public void addAppartment(ApartmentDTO app,  Integer persons) {
		ApartmentPeriod apartmentPeriod = new ApartmentPeriod();
		
		apartmentPeriod.setApartment(app);
		apartmentPeriod.setPersons(persons);
		
		this.addAppartment(apartmentPeriod);
	}

	public void addBudgetPlan(BudgetPlanDTO ba) {
		ba.setPeriode(this);

		bookingAccounts.add(ba);
	}

	public Date getEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 11); // 11 = december
		cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
		
		return cal.getTime();
	}

	public Date getStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 0); 
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return cal.getTime();
	}

	public void addAppartment(@Valid ApartmentPeriod a) {
		a.setPeriode(this);
		this.appartments.add(a);
	}
}
