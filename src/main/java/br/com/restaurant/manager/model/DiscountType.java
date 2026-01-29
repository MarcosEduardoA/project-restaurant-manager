package br.com.restaurant.manager.model;

public enum DiscountType {
	
	PERCENTAGE("Percentage"),
	FIXED("Fixed");
	
	private String value;
	
	private DiscountType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
