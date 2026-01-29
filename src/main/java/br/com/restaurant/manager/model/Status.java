package br.com.restaurant.manager.model;

public enum Status {
	
	OPEN("Open"),
	PAID("Paid"),
	CANCELED("Canceled");
	
	private String value;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	private Status(String value) {
		this.value = value;
	}
}
