package de.marinek.propertymanager.domain.account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import de.marinek.propertymanager.domain.DataTransfereObject;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class TransactionDTO extends DataTransfereObject {

	@ManyToOne
	private AccountDTO account;
	
	@Transient
	@CsvBindByName(column = "Auftragskonto")
	private String accountIBAN;
	
	
	@Column
	@CsvBindByName(column = "Valutadatum", locale = "de")
	@CsvDate("dd.MM.yyyy")
	private Date date;

	@Column
	@CsvBindByName(column = "Buchungstext")
	private String text;
	
	@Column(length = 512)
	@CsvBindByName(column = "Verwendungszweck")
	private String usage;
	
	@Column
	@CsvBindByName(column = "Beguenstigter/Zahlungspflichtiger")
	private String fromName;
	
	@Column
	@CsvBindByName(column = "Kontonummer")
	private String fromAccountNumber;
	
	@Column
	@CsvBindByName(column = "betrag", locale = "de")
	private Double value;

}
