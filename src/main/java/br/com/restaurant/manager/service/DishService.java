package br.com.restaurant.manager.service;

import java.util.List;

import br.com.restaurant.manager.model.Dish;
import br.com.restaurant.manager.model.DishComposition;

public interface DishService {
	
	Dish createDish(Dish dish);
	
	Dish addDishComposition(Dish dish, DishComposition dishComposition);
	
	List<Dish> loadDishes();
	
}
