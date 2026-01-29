package br.com.restaurant.manager.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Sale implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String requestNumber;
	
	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Item> items = new ArrayList<>();
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate saleDate;
	
	private BigDecimal totalValue;
	
	@ManyToOne
	@JoinColumn(name = "discount_id")
	private Discount discount;
	
	private BigDecimal serviceTax;
	
	@Enumerated(EnumType.STRING)
	private Payment payment;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private String observation;
	
	public BigDecimal calculateTotalValue() {
		
		Double itemsTotalPrice = 0.0;
		for (Item i : items) {
			itemsTotalPrice += i.getTotalPrice().doubleValue();
		}
		
		Double totalPrice = 0.0;
		if (discount != null) {
			if (discount.getType().equals(DiscountType.PERCENTAGE)) {
				Double percentage = discount.getValue().doubleValue() / 100;
				totalPrice = itemsTotalPrice - itemsTotalPrice * percentage;
			}
			else if (discount.getType().equals(DiscountType.FIXED)) {
				totalPrice = itemsTotalPrice - discount.getValue().doubleValue();
			}
		}
		else {
			totalPrice = itemsTotalPrice;
		}
		
		return BigDecimal.valueOf(totalPrice + getServiceTax().doubleValue());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public LocalDate getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public Discount getDiscount() {
		return discount;
	}
	
	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
	
	public BigDecimal getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(BigDecimal serviceTax) {
		this.serviceTax = serviceTax;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
	
}
