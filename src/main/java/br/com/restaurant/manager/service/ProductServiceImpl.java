package br.com.restaurant.manager.service;

import org.springframework.stereotype.Service;

import br.com.restaurant.manager.model.Product;
import br.com.restaurant.manager.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;
	
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public Product createProduct(Product product) {
		
		return productRepository.save(product);
	}

}
