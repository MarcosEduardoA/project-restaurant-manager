package br.com.restaurant.manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.restaurant.manager.model.Dish;
import br.com.restaurant.manager.model.DishComposition;
import br.com.restaurant.manager.repository.DishRepository;

@Service
public class DishServiceImpl implements DishService {

	private final DishRepository dishRepository;
	
	public DishServiceImpl(DishRepository dishRepository) {
		this.dishRepository = dishRepository;
	}
	
	@Override
	public Dish createDish(Dish dish) {
		
		return dishRepository.save(dish);
	}

	@Override
	public Dish addDishComposition(Dish dish, DishComposition dishComposition) {
		
		dishComposition.setDish(dish);
		
		dish.getComposition().add(dishComposition);
		
		return dish;
	}

	@Override
	public List<Dish> loadDishes() {

		return dishRepository.findAll();
	}

}
