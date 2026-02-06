package br.com.restaurant.manager.service;

import java.math.BigDecimal;

import br.com.restaurant.manager.model.Discount;
import br.com.restaurant.manager.model.Item;
import br.com.restaurant.manager.model.Sale;

public interface SaleService {
	
	Sale addItem(Sale sale, Item item);
	
	BigDecimal calculateTotal(Sale sale);
	
	BigDecimal applyDiscount(BigDecimal total, Discount discount);
}
