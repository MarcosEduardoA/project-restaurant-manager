package br.com.restaurant.manager.model;

public enum UnitOfMeasurement {
	
	L("Liter"),
	ML("Milliliter"),
	KG("Kilogram"),
	G("Gram");
	
	private String value;
	
	private UnitOfMeasurement(String value) {
		this.value = value;
	}
	
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
