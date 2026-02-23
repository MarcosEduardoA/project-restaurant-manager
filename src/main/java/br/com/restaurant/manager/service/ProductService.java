package br.com.restaurant.manager.service;

import java.util.List;

import br.com.restaurant.manager.model.Product;

public interface ProductService {
	
	Product createProduct(Product product);
	
	List<Product> loadProducts();
	
}
