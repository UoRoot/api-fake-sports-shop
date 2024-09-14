package es.diplock.examples.dtos.product;

import java.math.BigDecimal;
import java.util.List;

import es.diplock.examples.enums.GenderEnum;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class  ProductDTO {
        Long id;
        String name;
        String description;
        String imageUrl;
        BigDecimal price;
        Integer stockQuantity;
        String gender;
        List<Integer> sizesIds;
        List<Integer> colorsIds;
        Integer subcategoryId;
        Integer brandId;
        
        public ProductDTO(Long id, String name, String description, String imageUrl, BigDecimal price,
                        Integer stockQuantity, GenderEnum gender, List<Integer> sizesIds, List<Integer> colorsIds,
                        Integer subcategoryId, Integer brandId) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.imageUrl = imageUrl;
                this.price = price;
                this.stockQuantity = stockQuantity;
                this.gender = gender.getDescription();
                this.sizesIds = sizesIds;
                this.colorsIds = colorsIds;
                this.subcategoryId = subcategoryId;
                this.brandId = brandId;
        }

        
}