package br.com.restaurant.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.restaurant.manager.utilities.SaleMapper;

@SpringBootTest
class ProjectRestaurantManagerApplicationTests {

	@MockBean
	private SaleMapper saleMapper;
	
	@Test
	void contextLoads() {
	}

}
