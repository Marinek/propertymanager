package de.marinek.propertymanager.domain.account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;

import de.marinek.propertymanager.components.converter.StringToIBANConverter;
import de.marinek.propertymanager.components.utils.SHA256Checksum;
import de.marinek.propertymanager.domain.DataTransfereObject;
import de.marinek.propertymanager.domain.partner.CreditorDTO;
import de.marinek.propertymanager.domain.plan.BudgetPlanDTO;
import lombok.Getter;
import lombok.Setter;
import nl.garvelink.iban.IBAN;

@Entity
@Getter
@Setter
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class TransactionDTO extends DataTransfereObject {

	@ManyToOne
	private AccountDTO account;
	
	@Transient
	@CsvCustomBindByName(column = "Auftragskonto", converter = StringToIBANConverter.class)
	private IBAN accountIBAN;
	
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
	@Convert(converter = StringToIBANConverter.class)
	@CsvCustomBindByName(column = "Kontonummer", converter = StringToIBANConverter.class)
	private IBAN fromAccountNumber;
	
	@Column
	@CsvBindByName(column = "betrag", locale = "de")
	private Double value;
	
	@Column(unique = true)
	private String checksum;
	
	@OneToOne
	private BudgetPlanDTO budgetPlan;
	
	@Transient
	private CreditorDTO creditor;
	
	@PrePersist
	private void calucateChecksum() {
		checksum = SHA256Checksum.checksum(usage, String.valueOf(date), String.valueOf(value));
	}
	

}
