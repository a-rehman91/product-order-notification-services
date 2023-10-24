package com.ms.inventory.service;

import com.ms.inventory.service.model.Inventory;
import com.ms.inventory.service.repositories.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

/*	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){

		return args -> {

			Inventory inventory1 = Inventory.builder()
					.skuCode("ihpone_13")
					.quantity(100)
					.build();

			Inventory inventory2 = Inventory.builder()
					.skuCode("ihpone_13_Red")
					.quantity(10)
					.build();

			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);

		};
	}*/
}
