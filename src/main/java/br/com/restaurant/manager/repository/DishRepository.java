package br.com.restaurant.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.restaurant.manager.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {

}
