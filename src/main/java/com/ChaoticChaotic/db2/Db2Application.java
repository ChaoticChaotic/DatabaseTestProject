package com.ChaoticChaotic.db2;

import com.ChaoticChaotic.db2.entity.Item;
import com.ChaoticChaotic.db2.entity.Shipping;
import com.ChaoticChaotic.db2.entity.Town;
import com.ChaoticChaotic.db2.repository.ItemRepository;
import com.ChaoticChaotic.db2.repository.ShippingRepository;
import com.ChaoticChaotic.db2.repository.TownRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



import java.time.LocalDate;



@SpringBootApplication
public class Db2Application {


	public static void main(String[] args) {
		SpringApplication.run(Db2Application.class, args);
	}




	/*@Bean
	CommandLineRunner commandLineRunner(ItemRepository itemRepository,
										ShippingRepository shippingRepository,
										TownRepository townRepository) {
		return args -> {

			Item item1 = new Item("Fork", 156L);
			Item item2 = new Item("Scoop", 1563L);
			itemRepository.save(item1);
			itemRepository.save(item2);
			Town town1 = new Town("Moscow",850L);
			Town town2 = new Town("Kazan",400L);
			townRepository.save(town1);
			townRepository.save(town2);
			LocalDate date = LocalDate.now();
			LocalDate date2 = LocalDate.of(2021,12,21);
			Shipping shipping1 = new Shipping(date,date2,town1,item2);
			Shipping shipping2 = new Shipping(date,date2,town2,item1);
			shippingRepository.save(shipping1);
			shippingRepository.save(shipping2);
		};
	}*/

}


