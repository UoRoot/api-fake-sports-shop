package es.diplock.examples.dtos.product;

import java.math.BigDecimal;
import java.util.List;

public record ProductDTO(
        Long id,
        String name,
        String description,
        String imageURL,
        BigDecimal price,
        Integer stockQuantity,
        String gender,
        List<Integer> sizesIds,
        List<Integer> colorsIds,
        Integer categoryId,
        Integer brandId) {
}