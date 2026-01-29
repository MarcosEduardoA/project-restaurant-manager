package br.com.restaurant.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.restaurant.manager.model.Product;
import br.com.restaurant.manager.repository.ProductRepository;

@Controller
@RequestMapping("/manager")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/stock-manager")
	public ModelAndView init() {
		ModelAndView modelAndView = new ModelAndView("manager/stock-manager");
		
		modelAndView.addObject("product", new Product());
		
		return modelAndView;
	}
	
	@PostMapping("add-stock")
	public ModelAndView add(Product product) {
		
		ModelAndView modelAndView = new ModelAndView("manager/stock-manager");
		productRepository.save(product);
		modelAndView.addObject("product", new Product());
		modelAndView.addObject("msg", "Product added to stock");
		
		return modelAndView;
	}
	
	public ProductRepository getProductRepository() {
		return productRepository;
	}
	
	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
}
