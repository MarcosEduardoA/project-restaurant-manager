package br.com.restaurant.manager.service;

import org.springframework.stereotype.Service;

import br.com.restaurant.manager.model.Discount;
import br.com.restaurant.manager.repository.DiscountRepository;

@Service
public class DiscountServiceImpl implements DiscountService{
	
	private final DiscountRepository discountRepository;
	
	public DiscountServiceImpl(DiscountRepository discountRepository) {
		this.discountRepository = discountRepository;
	}
	
	@Override
	public Discount createDiscount(Discount discount) {
		
		return discountRepository.save(discount);
	}

}
