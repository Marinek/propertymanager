package de.marinek.propertymanager.domain.accounting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
	
	@Column
	private String name;
	
	@Enumerated(EnumType.STRING)
	private AccountType type;
	
	@Enumerated(EnumType.STRING)
	private DistributionKey distributionkey;
	
}
