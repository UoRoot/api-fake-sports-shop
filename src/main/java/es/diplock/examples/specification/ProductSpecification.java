package es.diplock.examples.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import es.diplock.examples.dtos.product.ProductSearchCriteriaDTO;
import es.diplock.examples.entities.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


public class ProductSpecification implements Specification<Product> {

    private final ProductSearchCriteriaDTO criteria;

    public ProductSpecification(ProductSearchCriteriaDTO criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        
        if (criteria.getGender() != null) {
            predicates.add(cb.equal(root.get("gender"), criteria.getGender().toUpperCase()));
        }

        if (criteria.getSortBy() != null && criteria.getSortingDirection() != null) {
            String direction = criteria.getSortBy().toLowerCase();
            String field = criteria.getSortingDirection();
            if ("desc".equals(direction)) {
                query.orderBy(cb.desc(root.get(field)));
            } else {
                query.orderBy(cb.asc(root.get(field)));
            }
        }
        
        if (criteria.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice()));
        }


        if (criteria.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }


}
