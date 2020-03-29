package de.marinek.propertymanager.domain.account;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import de.marinek.propertymanager.domain.DataTransfereObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AccountDTO extends DataTransfereObject {

	@Column(nullable =  false, unique = true)
	private String iban;
	
	@Column
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
	@OrderBy("date desc")
	private Set<TransactionDTO> transactions;
	
}
