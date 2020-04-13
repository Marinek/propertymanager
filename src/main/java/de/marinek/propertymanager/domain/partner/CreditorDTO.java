package de.marinek.propertymanager.domain.partner;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "creditors")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue(value = "C")
public class CreditorDTO extends PartnerDTO {

	@Override
	public String getPartnerIdent() {
		return this.getName() + " (Firma)";
	}

	
}
