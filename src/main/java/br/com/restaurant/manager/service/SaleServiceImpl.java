package br.com.restaurant.manager.service;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.restaurant.manager.model.Discount;
import br.com.restaurant.manager.model.DiscountType;
import br.com.restaurant.manager.model.Item;
import br.com.restaurant.manager.model.Sale;
import br.com.restaurant.manager.repository.SaleRepository;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
	
	private final SaleRepository saleRepository;
	
	public SaleServiceImpl(SaleRepository saleRepository) {
		this.saleRepository = saleRepository;
	}
	
	@Override
	public Sale createSale(Sale sale) {
		
		BigDecimal total = calculateTotal(sale);
		sale.setTotalValue(total);
		
		return saleRepository.save(sale);
	}
	
	@Override
	public Sale addItem(Sale sale, Item item) { /*Adiciona items na lista do Sale*/
		
		item.setSale(sale); 
		
		BigDecimal totalValue = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())).setScale(2);
		
		item.setTotalPrice(totalValue);
		sale.getItems().add(item);
		
		return sale;
	}

	@Override
	public BigDecimal calculateTotal(Sale sale) {
		
		// Pega o valor total somado de todos os items
		BigDecimal total = sale.getItems().stream().map(Item::getTotalPrice)
				.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		if (sale.getDiscount() != null) {
			total = applyDiscount(total, sale.getDiscount());
		}
		
		if (sale.getServiceTax() != null) {
			BigDecimal serviceTaxValue = total.multiply(BigDecimal.valueOf
					(sale.getServiceTax()).divide(BigDecimal.valueOf(100)));
			
			total = total.add(serviceTaxValue);
		}
		
		return total.setScale(2);
	}

	@Override
	public BigDecimal applyDiscount(BigDecimal total, Discount discount) {
		
		if (discount.getType().equals(DiscountType.PERCENTAGE)) {
			total = total.subtract(total.multiply(discount.getValue().divide(BigDecimal.valueOf(100))));
			System.out.println(total.multiply(discount.getValue().divide(BigDecimal.valueOf(100))));
		}
		else if (discount.getType().equals(DiscountType.FIXED)) {
			total = total.subtract(discount.getValue());
			System.out.println(discount.getValue());
		}
		
		return total;
	}

}
