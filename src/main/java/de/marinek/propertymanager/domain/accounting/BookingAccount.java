package de.marinek.propertymanager.domain.accounting;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.marinek.propertymanager.domain.DataTransfereObject;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="bookingaccounts")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BookingAccount extends DataTransfereObject {
	
	private String name;
	
	@OneToOne
	private PartnerDTO partner;
	
	private String externReference;
	
}
