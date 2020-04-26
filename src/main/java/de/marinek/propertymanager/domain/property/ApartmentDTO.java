package de.marinek.propertymanager.domain.property;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.marinek.propertymanager.domain.DataTransfereObject;
import de.marinek.propertymanager.domain.partner.OwnerDTO;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "apartments")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ApartmentDTO extends DataTransfereObject{

	@Column
	private String name;
	
	@Column
	private double shares;
	
	@Column
	private double area;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private OwnerDTO owner;
}
