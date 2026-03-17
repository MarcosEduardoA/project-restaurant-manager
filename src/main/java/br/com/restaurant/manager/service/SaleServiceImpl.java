package br.com.restaurant.manager.service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.restaurant.manager.model.Discount;
import br.com.restaurant.manager.model.DiscountType;
import br.com.restaurant.manager.model.DishComposition;
import br.com.restaurant.manager.model.Item;
import br.com.restaurant.manager.model.Sale;
import br.com.restaurant.manager.repository.ProductRepository;
import br.com.restaurant.manager.repository.SaleRepository;
import br.com.restaurant.manager.utilities.SaleMapper;

@Service
public class SaleServiceImpl implements SaleService {

	private final SaleRepository saleRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SaleMapper saleMapper;
	
	private String msg;
	
	private String totalPriceItem;
	
	public SaleServiceImpl(SaleRepository saleRepository) {
		this.saleRepository = saleRepository;
	}
	
	@Override
	@Transactional
	public Sale createSale(Sale sale) throws Exception { // Salva o objeto Sale
		
		Sale saleToSave; // Sale que será salvo
		
		if (sale.getId() == null) {
			saleToSave = sale;
			saleToSave.setRequestNumber(generateSaleNumber());
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
		
		msg = "Saved successfully!";
		return saleRepository.save(saleToSave); // Salva o Sale
	}
	
	@Override
	public Sale addItem(Sale sale, Item item) { /*Adiciona items na lista do Sale*/
		
		for (DishComposition dishComposition : item.getDish().getComposition()) {
			Integer currentStockQuantity = productRepository.getProductStockQuantityById(dishComposition.getProduct().getId());
			Integer updatedStockQuantity = currentStockQuantity - dishComposition.getQuantity().intValue() * item.getQuantity();
			if (updatedStockQuantity < 0) {
				msg = "Insuficient ingredients in stock";
				totalPriceItem = "R$ 0.00";
			}
			else {
				item.setSale(sale);
				item.setUnitPrice(item.getDish().getPrice());
				
				BigDecimal totalValue = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())).setScale(2);
				
				item.setTotalPrice(totalValue);
				sale.getItems().add(item);
				productRepository.updateProductStockQuantity(updatedStockQuantity, dishComposition.getProduct().getId());
				totalPriceItem = "R$ " + item.getTotalPrice();
			}
		}
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
		}
		else if (discount.getType().equals(DiscountType.FIXED)) {
			total = total.subtract(discount.getValue());
		}
		
		return total;
	}
	
	@Override
	public String generateSaleNumber() {
		
		String numbers = "0123456789";
		
		StringBuilder sb = new StringBuilder();
		
		Random random = new Random();
		
		for (int i = 0; i < 6; i++) { // Ira sortear os números 6 vezes
			int index = random.nextInt(numbers.length()); // Posição sorteada
			
			char randomChar = numbers.charAt(index); // Número sorteado
			
			sb.append(randomChar);
		}
		
		String generatedRequestNumber = sb.toString();
		
		return generatedRequestNumber;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public String getTotalPriceItem() {
		return totalPriceItem;
	}
	
}
