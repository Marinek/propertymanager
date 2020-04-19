package de.marinek.propertymanager.domain.partner;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import de.marinek.propertymanager.components.converter.StringToIBANConverter;
import de.marinek.propertymanager.domain.DataTransfereObject;
import lombok.Getter;
import lombok.Setter;
import nl.garvelink.iban.IBAN;

@Getter
@Setter
@Entity
@Table(name = "partners")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="partnertype",discriminatorType=DiscriminatorType.STRING)
public abstract class PartnerDTO extends DataTransfereObject {

	@Column
	@Convert(converter = StringToIBANConverter.class)
	private IBAN iban;

	private String name;

	public abstract String getPartnerIdent();	

	public void setIban(String iban) {
		this.iban = IBAN.valueOf(iban);
	}
	public void setIban(IBAN iban) {
		this.iban = iban;
	}
}
