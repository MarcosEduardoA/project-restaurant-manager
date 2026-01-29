package br.com.restaurant.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.restaurant.manager.model.Sale;

@Repository
@Transactional
public interface SaleRepository extends JpaRepository<Sale, Long> {
	
}
