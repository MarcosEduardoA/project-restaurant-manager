package br.com.restaurant.manager.model;

public enum Payment {
	
	CASH("Cash"),
	CREDIT("Credit"),
	DEBIT("Debit"),
	MEAL_VOUCHER("Meal Voucher"),
	PIX("Pix");
	
	private String value;
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	private Payment(String value) {
		this.value = value;
	}
	
	
}
