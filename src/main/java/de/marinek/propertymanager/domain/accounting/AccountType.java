package de.marinek.propertymanager.domain.accounting;

public enum AccountType {
	
	INCOME ("Einnahmen"), EXPENSE ("Ausgaben"), DEFAULT("N/A");
	
	private String name;
	
	private AccountType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name; 
	}
}
