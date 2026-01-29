package br.com.restaurant.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "br.com.restaurant.manager.model")
@ComponentScan(basePackages = {"br.*"})
@EnableTransactionManagement
public class ProjectRestaurantManagerApplication{

	public static void main(String[] args) {
		SpringApplication.run(ProjectRestaurantManagerApplication.class, args);
	}

}
	