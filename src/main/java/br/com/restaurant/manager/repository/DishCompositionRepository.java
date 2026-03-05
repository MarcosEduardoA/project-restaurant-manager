package br.com.restaurant.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.restaurant.manager.model.DishComposition;

public interface DishCompositionRepository extends JpaRepository<DishComposition, Long> {
	
	@Query("select d from DishComposition d where d.dish.id = ?1 ")
	List<DishComposition> findDishCompositionByDishId(Long id);
}
