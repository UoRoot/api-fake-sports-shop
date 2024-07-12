package es.diplock.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class ExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamplesApplication.class, args);
	}

	// @Bean
    // CommandLineRunner commandLineRunner(/* repository*/){
	// 	return args -> {
			
	// 		// Price price = new Price();
	// 		// price.setBasePrice(BigDecimal.valueOf(200));
	// 		// price.setTotalTax(BigDecimal.valueOf(50));
	// 		// price.setTotalPrice(BigDecimal.valueOf(150));

	// 		// priceRepository.save(price);

	// 		// Optional<Price> optionalPrice = priceRepository.findById(2L);
	// 		// if  (optionalPrice.isPresent()) {
	// 		// 	System.out.println(optionalPrice.get());
	// 		// }
	// 	};
	// }
}
