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
import java.util.List;


@SpringBootApplication
public class Db2Application {


	public static void main(String[] args) {
		SpringApplication.run(Db2Application.class, args);
	}




	@Bean
	CommandLineRunner commandLineRunner(ItemRepository itemRepository,
										ShippingRepository shippingRepository,
										TownRepository townRepository) {
		return args -> {

			Item item = Item.builder()
					.name("Fork")
					.quantity(156L)
					.build();
			Item item1 = Item.builder()
					.name("Scoop")
					.quantity(1563L)
					.build();
			itemRepository.save(item);
			itemRepository.save(item1);
			Town town = Town.builder()
					.name("Moscow")
					.distance(850L)
					.build();
			Town town1 = Town.builder()
					.name("Kazan")
					.distance(400L)
					.build();
			townRepository.save(town);
			townRepository.save(town1);
			LocalDate date = LocalDate.now();
			LocalDate date1 = LocalDate.of(2022,05,21);
			Shipping shipping1 = Shipping.builder()
					.startDate(date)
					.endDate(date1)
					.fromTown(town)
					.toTown(town1)
					.items(List.of(item,item1))
					.build();
			shippingRepository.save(shipping1);
		};
	}

}


