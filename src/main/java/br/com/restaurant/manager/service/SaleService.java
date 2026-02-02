package br.com.restaurant.manager.service;

import br.com.restaurant.manager.model.Item;
import br.com.restaurant.manager.model.Sale;

public interface SaleService {
	
	Sale addItem(Sale sale, Item item);
	
}
