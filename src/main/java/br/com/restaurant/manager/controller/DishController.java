package br.com.restaurant.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import br.com.restaurant.manager.model.Dish;
import br.com.restaurant.manager.model.DishComposition;
import br.com.restaurant.manager.service.DishService;
import br.com.restaurant.manager.service.ProductService;

@Controller
@RequestMapping("/manager")
@SessionAttributes("dish")
public class DishController {
	
	private final DishService dishService;
	
	private final ProductService productService;
	
	public DishController(DishService dishService, ProductService productService) {
		this.dishService = dishService;
		this.productService = productService;
	}
	
	@ModelAttribute("dish")
	public Dish getDish() {
		return new Dish();
	}
	
	@GetMapping("/recipe-manager")
	public ModelAndView init() {
		
		ModelAndView modelAndView = new ModelAndView("manager/recipe-manager");
		
		modelAndView.addObject("dish", new Dish());
		modelAndView.addObject("composition", new DishComposition());
		modelAndView.addObject("products", productService.loadProducts());
		
		return modelAndView;
	}
	
	@PostMapping("save-dish")
	public ModelAndView save(@ModelAttribute("dish") Dish dish) {
		
		ModelAndView modelAndView = new ModelAndView("manager/recipe-manager");
		
		dishService.createDish(dish); /*Salva o Dish*/
		
		modelAndView.addObject("dish", new Dish());
		modelAndView.addObject("composition", new DishComposition());
		modelAndView.addObject("products", productService.loadProducts());
		modelAndView.addObject("msg", "Dish saved successfully");
		
		return modelAndView;
	}
	
	@PostMapping("add-composition")
	public ModelAndView addCompostion(@ModelAttribute("dish") Dish dish,
			@ModelAttribute("dishComposition") DishComposition composition) {
		
		ModelAndView modelAndView = new ModelAndView("manager/recipe-manager");
		
		dishService.addDishComposition(dish, composition);
		
		modelAndView.addObject("composition", new DishComposition());
		modelAndView.addObject("products", productService.loadProducts());
		modelAndView.addObject("msg", "Composition addead successfully");
		
		return modelAndView;
	}
}
