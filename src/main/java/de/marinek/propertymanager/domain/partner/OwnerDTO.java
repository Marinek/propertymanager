package de.marinek.propertymanager.domain.partner;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "owners")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue(value = "O")
public class OwnerDTO extends PartnerDTO {

	private String surname;
	
}
