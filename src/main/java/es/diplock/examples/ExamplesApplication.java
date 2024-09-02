package es.diplock.examples;

// import java.util.List;

// import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

// import es.diplock.examples.entities.Product;
// import jakarta.persistence.EntityManager;
// import jakarta.persistence.criteria.CriteriaBuilder;
// import jakarta.persistence.criteria.CriteriaQuery;
// import jakarta.persistence.criteria.Predicate;
// import jakarta.persistence.criteria.Root;

@RestController
@SpringBootApplication
public class ExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamplesApplication.class, args);
	}
	// 	ApplicationContext context = SpringApplication.run(ExamplesApplication.class, args);
	// 	ProductRepository userRepository = context.getBean(ProductRepository.class);

	// 	List<Product> products = userRepository.findByPriceLessThan(Double.valueOf(100), PageRequest.of(1, 2));

	// 	products.stream().forEach(p -> System.out.println(p));

	// @Bean
	// CommandLineRunner commandLineRunner(EntityManager entityManager){
	// 	return args -> {
	// 		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	// 		CriteriaQuery<Product> query = cb.createQuery(Product.class);
	// 		Root<Product> product = query.from(Product.class);

	// 		Predicate namePredicate = cb.equal(product.get("name"), "Sport Vest");
	// 		query.where(namePredicate);

	// 		List<Product> products = entityManager.createQuery(query).getResultList();
	// 		products.forEach(System.out::println);
	// 		//System.out.println(products);
	// 	};
	// }
}
