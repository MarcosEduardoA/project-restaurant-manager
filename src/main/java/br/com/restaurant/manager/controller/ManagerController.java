package br.com.restaurant.manager.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@ModelAttribute("sale")
	public Sale getSale() {
		return new Sale();
	}
	
	@GetMapping("/sale-manager")
	public ModelAndView init(@ModelAttribute("sale") Sale sale) {
		
		ModelAndView modelAndView = new ModelAndView("manager/sale-manager");
		
		modelAndView.addObject("sale", new Sale());
		modelAndView.addObject("products", productRepository.findAll());
		modelAndView.addObject("discounts", discountRepository.findAll());
		modelAndView.addObject("item", new Item());
		
		return modelAndView;
	}
	
	@PostMapping("save-sale")
	public ModelAndView save(@ModelAttribute("sale") Sale sale) throws Exception {
		
		Sale savedSale = saleService.createSale(sale);   // Salva a venda
		
		ModelAndView modelAndView = new ModelAndView("redirect:/manager/edit/" + savedSale.getId()); // Redireciona para a edição de Sale
		
		return modelAndView;
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView update(@PathVariable("id") Long id, @ModelAttribute("sale") Sale sale,
			@ModelAttribute("item") Item item) {
		
		ModelAndView modelAndView = new ModelAndView("manager/sale-manager");
		
		Sale saleUpdate = saleRepository.findById(id).orElse(new Sale()); // Encontra o Sale já cadastrado
		
		modelAndView.addObject("totalPrice", "R$ " + saleUpdate.getTotalValue());
		modelAndView.addObject("sale", saleUpdate); // Limpa o objeto de venda
		modelAndView.addObject("item", new Item()); // Cria um novo Item
		modelAndView.addObject("msg", "Saved successfully!"); // Emite a mensagem de sucesso
		modelAndView.addObject("products", productRepository.findAll()); // Carrega todos os produtos
		modelAndView.addObject("discounts", discountRepository.findAll()); // Carrega todos os descontos
		
		return modelAndView;
	}
	
	@PostMapping("add-item")
	public ModelAndView addItem(@ModelAttribute("item") Item item, 
			@ModelAttribute("sale") Sale sale) {
		
		ModelAndView modelAndView = new ModelAndView("manager/sale-manager");
		
		saleService.addItem(sale, item);
		modelAndView.addObject("totalPriceItem", "R$ " + item.getTotalPrice());
		modelAndView.addObject("item", new Item());
		modelAndView.addObject("products", productRepository.findAll());
		modelAndView.addObject("discounts", discountRepository.findAll());
		return modelAndView;
		
	}
	
	@GetMapping("/new")
	public ModelAndView newSale(SessionStatus sessionStatus) {
		
		sessionStatus.setComplete();
		
		ModelAndView modelAndView = new ModelAndView("manager/sale-manager");
		
		modelAndView.addObject("sale", new Sale());
		modelAndView.addObject("products", productRepository.findAll());
		modelAndView.addObject("discounts", discountRepository.findAll());
		modelAndView.addObject("item", new Item());
		
		return modelAndView;
		
	}
	
	public SaleRepository getSaleRepository() {
		return saleRepository;
	}
	
	public void setSaleRepository(SaleRepository saleRepository) {
		this.saleRepository = saleRepository;
	}
}
