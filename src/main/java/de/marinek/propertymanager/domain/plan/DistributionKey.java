package de.marinek.propertymanager.domain.plan;

import lombok.Getter;

@Getter
public enum DistributionKey {
	
	A_PP("A", "25 % der Gesamtsumme geteilt durch 3 Wohneinheiten\r\n" + 
			"75 % der Gesamtsumme geteilt durch Anzahl der Personen"),
	B_AREA("B", "Gesamtsumme geteilt durch 3 Wohneinheiten"),
	C_EQUAL("C", "Gesamtsumme geteilt durch 3 Wohneinheiten"),
	D_ASSETS("D", "Gesamtsumme geteilt durch Eigentümeranteile"),
	
	//Einnahmen
	PM_INCOME("Monatlich", "Vorrauszahung pro Monat"),
	PA_INCOME("Jährlich", "Sonstige Einkünfte Jährlich");
	
	private String name;
	
	private String description;
	
	private DistributionKey(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
