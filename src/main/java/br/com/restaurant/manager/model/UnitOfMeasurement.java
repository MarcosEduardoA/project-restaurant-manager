package br.com.restaurant.manager.model;

public enum UnitOfMeasurement {
	
	Liter("L"),
	Milliliter("ml"),
	Kilogram("kg"),
	Gram("g");
	
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
