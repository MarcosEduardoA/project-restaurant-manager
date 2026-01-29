package br.com.restaurant.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.restaurant.manager.model.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
