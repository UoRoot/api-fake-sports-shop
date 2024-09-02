package es.diplock.examples.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchCriteriaDTO {
    private int page = 1;
    private int size = 5;
    private Double minPrice;
    private Double maxPrice;
    private String gender;
    private String sortBy;
    private String sortingDirection;
    // private String brand;
    // private String sizes;
    // private String colors;
}

