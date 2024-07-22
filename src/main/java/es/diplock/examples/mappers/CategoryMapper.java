package es.diplock.examples.mappers;

import es.diplock.examples.dtos.CategoryDTO;
import es.diplock.examples.entities.Category;

public class CategoryMapper {
    public static CategoryDTO toDto(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDTO(category.getId(), category.getName());
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryDTO.id());
        category.setName(categoryDTO.name());
        return category;
    }
}