package de.marinek.propertymanager.components.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import nl.garvelink.iban.IBAN;

@Converter(autoApply = true)
public class StringToIBANConverter extends AbstractBeanField<IBAN> implements AttributeConverter<IBAN, String> {
	
	@Override
	public String convertToDatabaseColumn(IBAN attribute) {
		if(attribute == null) {
			return "";
		}
		
		return attribute.toPlainString();
	}

	@Override
	public IBAN convertToEntityAttribute(String dbData) {
		if(dbData != null && dbData.length() == 0) {
			dbData = null;
		}
		
		return IBAN.valueOf(dbData);
	}

	@Override
	protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		return convertToEntityAttribute(value);
	}
}
