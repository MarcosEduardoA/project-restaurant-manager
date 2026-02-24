package br.com.restaurant.manager.service;

import java.math.BigDecimal;
import java.util.Objects;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.restaurant.manager.model.Discount;
import br.com.restaurant.manager.model.DiscountType;
import br.com.restaurant.manager.model.Item;
import br.com.restaurant.manager.model.Sale;
import br.com.restaurant.manager.repository.SaleRepository;
import br.com.restaurant.manager.utilities.SaleMapper;

@Service
public class SaleServiceImpl implements SaleService {
	
	private final SaleRepository saleRepository;
	
	@Autowired
	private SaleMapper saleMapper;
	
	public SaleServiceImpl(SaleRepository saleRepository) {
		this.saleRepository = saleRepository;
	}
	
	@Override
	@Transactional
	public Sale createSale(Sale sale) throws Exception { // Salva o objeto Sale
		
		Sale saleToSave; // Sale que será salvo
		
		if (sale.getId() == null) {
			saleToSave = sale;
		}
		else{
			saleToSave = saleRepository.findById(sale.getId()).orElse(new Sale());
			saleMapper.updateSale(sale, saleToSave); // Passa o Sale recebido do banco para o Sale que será salvo (atualizado)
			
			BigDecimal total = calculateTotal(saleToSave); // Calcula o valor total de Sale
			saleToSave.setTotalValue(total);
			
			return saleRepository.save(saleToSave); // Atualiza o Sale
		}
		
		BigDecimal total = calculateTotal(saleToSave); // Calcula o valor total de Sale
		saleToSave.setTotalValue(total);
		
		return saleRepository.save(saleToSave); // Salva o Sale
	}
	
	@Override
	public Sale addItem(Sale sale, Item item) { /*Adiciona items na lista do Sale*/
		
		item.setSale(sale);
		item.setUnitPrice(item.getDish().getPrice());
		
		BigDecimal totalValue = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())).setScale(2);
		
		item.setTotalPrice(totalValue);
		sale.getItems().add(item);
		
		return sale;
	}

	@Override
	public BigDecimal calculateTotal(Sale sale) { // Calcula o valor total de Sale
		
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
	public BigDecimal applyDiscount(BigDecimal total, Discount discount) { // Aplica o desconto ao valor de Sale
		
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
