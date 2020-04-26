package de.marinek.propertymanager.domain.plan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import de.marinek.propertymanager.domain.DataTransfereObject;
import de.marinek.propertymanager.domain.property.ApartmentDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "apartment_period", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"apartment_id", "periode_id"})
	}) 
public class ApartmentPeriod extends DataTransfereObject {

	@ManyToOne
	private ApartmentDTO apartment;
	
	@ManyToOne
	private PeriodDTO periode;
	
	@Column
	private Integer persons;

}
