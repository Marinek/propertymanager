package de.marinek.propertymanager.domain.account;

import javax.persistence.Column;
import javax.persistence.Entity;

import de.marinek.propertymanager.domain.DataTransfereObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AccountDTO extends DataTransfereObject {

	@Column
	private String name;
	
	@Column
	private String description;
	
}
