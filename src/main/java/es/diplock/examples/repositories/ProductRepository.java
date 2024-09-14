package es.diplock.examples.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.diplock.examples.dtos.product.ProductDTO;
import es.diplock.examples.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("SELECT new es.diplock.examples.dtos.product.ProductDTO(p.id, p.name, p.description, p.imageUrl, p.price, p.stockQuantity, p.gender, " +
            "null, null, " +
            "p.subcategory.id, p.brand.id) FROM Product p")
    Page<ProductDTO> findAllProjectedBy(Specification<Product> spec, Pageable pageable);
}
