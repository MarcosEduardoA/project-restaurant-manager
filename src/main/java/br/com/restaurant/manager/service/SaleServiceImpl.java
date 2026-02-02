package br.com.restaurant.manager.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.restaurant.manager.model.Item;
import br.com.restaurant.manager.model.Sale;

@Service
public class SaleServiceImpl implements SaleService {
	
	@Override
	public Sale addItem(Sale sale, Item item) { /*Adiciona items na lista do Sale*/
		
		item.setSale(sale); 
		
		BigDecimal totalValue = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
		
		item.setTotalPrice(totalValue);
		sale.getItems().add(item);
		
		return sale;
	}

	
	
}
