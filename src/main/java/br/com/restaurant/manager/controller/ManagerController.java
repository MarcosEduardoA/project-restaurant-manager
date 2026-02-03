package br.com.restaurant.manager.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import br.com.restaurant.manager.model.Item;
import br.com.restaurant.manager.model.Sale;
import br.com.restaurant.manager.repository.DiscountRepository;
import br.com.restaurant.manager.repository.ProductRepository;
import br.com.restaurant.manager.repository.SaleRepository;
import br.com.restaurant.manager.service.SaleService;

@Controller
@RequestMapping("/manager")
@SessionAttributes("sale")
public class ManagerController {
	
	private final SaleService saleService;
	
	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private DiscountRepository discountRepository;
	
	public ManagerController(SaleService saleService) {
		this.saleService = saleService;
	}
	
	@GetMapping("/sale-manager")
	public ModelAndView init() {
		
		ModelAndView modelAndView = new ModelAndView("manager/sale-manager");
		
		modelAndView.addObject("sale", new Sale());
		modelAndView.addObject("products", productRepository.findAll());
		modelAndView.addObject("discounts", discountRepository.findAll());
		modelAndView.addObject("item", new Item());
		
		return modelAndView;
	}
	
	@PostMapping("save-sale")
	public ModelAndView save(@ModelAttribute Sale sale, 
			@ModelAttribute Item item, SessionStatus sessionStatus) {
		
		ModelAndView modelAndView = new ModelAndView("manager/sale-manager");
		
		//sale.setTotalValue(sale.calculateTotalValue()); // Calcula valor total da venda
		sale.setTotalValue(saleService.calculateTotal(sale));
		saleRepository.save(sale); // Salva a venda
		sessionStatus.setComplete(); // Limpa a sessão
		modelAndView.addObject("sale", new Sale()); // Limpa o objeto de venda
		modelAndView.addObject("msg", "Saved successfully!"); // Emite a mensagem de sucesso
		modelAndView.addObject("products", productRepository.findAll()); // Carrega todos os produtos
		modelAndView.addObject("discounts", discountRepository.findAll());
		
		return modelAndView;
	}
	
	@PostMapping("add-item")
	public ModelAndView addItem(@ModelAttribute Item item, 
			@ModelAttribute Sale sale) {
		
		ModelAndView modelAndView = new ModelAndView("manager/sale-manager");
		
		saleService.addItem(sale, item);
		modelAndView.addObject("item", new Item());
		modelAndView.addObject("products", productRepository.findAll());
		modelAndView.addObject("discounts", discountRepository.findAll());
		return modelAndView;
		
	}
	
	public SaleRepository getSaleRepository() {
		return saleRepository;
	}
	
	public void setSaleRepository(SaleRepository saleRepository) {
		this.saleRepository = saleRepository;
	}
}
