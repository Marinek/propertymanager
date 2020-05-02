package de.marinek.propertymanager.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class DataTransfereObject {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
}
