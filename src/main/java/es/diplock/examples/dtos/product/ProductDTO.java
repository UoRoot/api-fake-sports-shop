package es.diplock.examples.dtos.product;

import java.math.BigDecimal;
import java.util.List;

public record ProductDTO(
        Long id,
        String name,
        String description,
        String imageUrl,
        BigDecimal price,
        Integer stockQuantity,
        String gender,
        List<Integer> sizesIds,
        List<Integer> colorsIds,
        Integer subcategoryId,
        Integer brandId) {
}