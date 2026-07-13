package br.com.restaurant.manager.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import br.com.restaurant.manager.model.Item;
import br.com.restaurant.manager.model.Sale;
import br.com.restaurant.manager.repository.DiscountRepository;
import br.com.restaurant.manager.repository.SaleRepository;
import br.com.restaurant.manager.service.DishService;
import br.com.restaurant.manager.service.SaleService;

@Controller
@RequestMapping("/manager")
@SessionAttributes("sale")
public class ManagerController {
	
	private final SaleService saleService;
	
	private final DishService dishService;
	
	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private DiscountRepository discountRepository;
	
	public ManagerController(SaleService saleService, DishService dishService) {
		this.saleService = saleService;
		this.dishService = dishService;
	}
	
	@ModelAttribute("sale")
	public Sale getSale() {
		return new Sale();
	}
	
	@GetMapping("/sale-manager")
	public ModelAndView init(@ModelAttribute("sale") Sale sale, 
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		
		ModelAndView modelAndView = new ModelAndView("manager/sale-manager");
		
		Sale newSale = new Sale();
		saleService.setCurrentDateSale(newSale);
		
		loadCommonData(modelAndView, page, size);
		modelAndView.addObject("sale", newSale);
		modelAndView.addObject("item", new Item());
		
		return modelAndView;
	}
	
	@PostMapping("save-sale")
	public ModelAndView save(@ModelAttribute("sale") Sale sale, @RequestParam("action") String action, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView("manager/sale-manager"); // Redireciona para a edição do Sale salvo
		
		if (action.equals("save")) {
			saleService.createSale(sale);
			
			modelAndView.addObject("sale", new Sale()); // Limpa o objeto de venda
			modelAndView.addObject("totalPrice", "R$ " + sale.getTotalValue()); // Pega o valor total de Sale
			modelAndView.addObject("item", new Item()); // Cria um novo Item
			modelAndView.addObject("msg", saleService.getMsg()); // Emite a mensagem de sucesso
			loadCommonData(modelAndView, page, size);
			//modelAndView.addObject("dishes", dishService.loadDishes()); // Carrega todos os pratos
			//modelAndView.addObject("discounts", discountRepository.findAll()); // Carrega todos os descontos
			//modelAndView.addObject("sales", saleRepository.findAll());
			
			
			return modelAndView;
		}
		else if (action.startsWith("remove-")) {
			
			int index = Integer.parseInt(action.split("-")[1]);
			
			if (sale.getItems() != null && index >= 0 && index < sale.getItems().size()) {
				sale.getItems().remove(index);
			}
			
			BigDecimal total = BigDecimal.ZERO;
			for (Item i : sale.getItems()) {
				total = total.add(i.getTotalPrice());
			}
			
			modelAndView.addObject("sale", sale); // Limpa o objeto de venda
			modelAndView.addObject("totalPrice", "R$ " + total); // Pega o valor total de Sale
			modelAndView.addObject("item", new Item()); // Cria um novo Item
			modelAndView.addObject("msg", saleService.getMsg()); // Emite a mensagem de sucesso
			modelAndView.addObject("dishes", dishService.loadDishes()); // Carrega todos os pratos
			modelAndView.addObject("discounts", discountRepository.findAll()); // Carrega todos os descontos
			modelAndView.addObject("sales", saleRepository.findAll());
			
			return modelAndView;
		}
		
		return modelAndView;
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView update(@PathVariable("id") Long id, @ModelAttribute("sale") Sale sale,
			@ModelAttribute("item") Item item, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		
		ModelAndView modelAndView = new ModelAndView("manager/sale-manager");
		
		Sale saleUpdate = saleRepository.findById(id).orElse(new Sale()); // Encontra o Sale já cadastrado
		
		modelAndView.addObject("sale", saleUpdate); // Limpa o objeto de venda
		modelAndView.addObject("totalPrice", "R$ " + saleUpdate.getTotalValue()); // Pega o valor total de Sale
		modelAndView.addObject("item", new Item()); // Cria um novo Item
		loadCommonData(modelAndView, page, size);
		
		return modelAndView;
	}
	
	@PostMapping("/add-item")
	public ModelAndView addItem(@ModelAttribute("item") Item item, 
			@ModelAttribute("sale") Sale sale, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		
		ModelAndView modelAndView = new ModelAndView("manager/sale-manager");
		
		saleService.addItem(sale, item);
		modelAndView.addObject("sale", sale); 
		modelAndView.addObject("totalPriceItem", saleService.getTotalPriceItem());
		modelAndView.addObject("item", new Item());
		loadCommonData(modelAndView, page, size);
		return modelAndView;
		
	}
	
	@GetMapping("/new")
	public ModelAndView newSale(SessionStatus sessionStatus, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		
		sessionStatus.setComplete();
		
		ModelAndView modelAndView = new ModelAndView("manager/sale-manager");
		
		Sale newSale = new Sale();
		saleService.setCurrentDateSale(newSale);
		
		modelAndView.addObject("sale", newSale);
		loadCommonData(modelAndView, page, size);
		modelAndView.addObject("item", new Item());
		
		return modelAndView;
		
	}
	
	@PostMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, @ModelAttribute("sale") Sale sale,
			@ModelAttribute("item") Item item, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		
		saleService.deleteSaleById(id);
		
		ModelAndView modelAndView = new ModelAndView("manager/sale-manager");
		
		modelAndView.addObject("sale", new Sale());
		modelAndView.addObject("msg", saleService.getMsg());
		loadCommonData(modelAndView, page, size);
		modelAndView.addObject("item", new Item());
		
		
		return modelAndView;
	}
	
	public void loadCommonData(ModelAndView mv, int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<Sale> salePage = saleRepository.findAll(pageable);
		
		mv.addObject("salesPage", salePage);
		mv.addObject("dishes", dishService.loadDishes());
		mv.addObject("discounts", discountRepository.findAll());
		
		int totalPages = salePage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			mv.addObject("pageNumbers", pageNumbers);
		}
		
	}
	
	public SaleRepository getSaleRepository() {
		return saleRepository;
	}
	
	public void setSaleRepository(SaleRepository saleRepository) {
		this.saleRepository = saleRepository;
	}
}
