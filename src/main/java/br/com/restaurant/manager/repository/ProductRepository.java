package br.com.restaurant.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.restaurant.manager.model.Product;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("select p from Product p where p.id = ?1")
	Product findProductById(Long id);
	
	@Modifying
	@Transactional
	@Query("update Product p set p.stockQuantity = ?1 where p.id = ?2")
	void updateProductStockQuantity(Integer stockQuantity ,Long id);
	
	@Query("select p.stockQuantity from Product p where p.id = ?1")
	Integer getProductStockQuantityById(Long id);
}
