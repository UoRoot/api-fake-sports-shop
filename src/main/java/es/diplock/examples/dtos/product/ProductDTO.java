package es.diplock.examples.dtos.product;

import java.math.BigDecimal;
import java.util.Set;

public record ProductDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String gender,
        Set<Integer> sizesIds,
        Set<Integer> colorsIds,
        Integer categoryId,
        Integer brandId) {
}