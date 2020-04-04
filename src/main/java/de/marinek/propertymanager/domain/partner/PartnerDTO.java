package de.marinek.propertymanager.domain.partner;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import de.marinek.propertymanager.domain.DataTransfereObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "partners")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="partnertype",discriminatorType=DiscriminatorType.STRING)
public abstract class PartnerDTO extends DataTransfereObject {
	
	private String iban;
	
	private String name;
	
}
