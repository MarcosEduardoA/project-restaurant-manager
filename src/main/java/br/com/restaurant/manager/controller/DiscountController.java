package br.com.restaurant.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.restaurant.manager.model.Discount;
import br.com.restaurant.manager.repository.DiscountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/manager")
public class DiscountController {
	
	@Autowired
	private DiscountRepository discountRepository;
	
	@GetMapping("/discount-manager")
	public ModelAndView init() {
		
		ModelAndView modelAndView = new ModelAndView("manager/discount-manager");
		
		modelAndView.addObject("discount", new Discount());
		
		return modelAndView;
		
	}
	
	@PostMapping("/save-discount")
	public ModelAndView save(@ModelAttribute Discount discount) {
		
		ModelAndView modelAndView = new ModelAndView("manager/discount-manager");
		
		discountRepository.save(discount);
		modelAndView.addObject("discount", new Discount());
		modelAndView.addObject("msg", "Discount saved successfully!");
		
		return modelAndView;
	}
	
	public DiscountRepository getDiscountRepository() {
		return discountRepository;
	}
	
	public void setDiscountRepository(DiscountRepository discountRepository) {
		this.discountRepository = discountRepository;
	}
}
