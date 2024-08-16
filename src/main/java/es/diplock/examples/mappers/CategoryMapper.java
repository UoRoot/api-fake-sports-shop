package es.diplock.examples.mappers;

import org.mapstruct.Mapper;

import es.diplock.examples.dtos.CategoryDTO;
import es.diplock.examples.entities.Category;

@Mapper
public interface CategoryMapper {

    Category toEntity(CategoryDTO categoryDTO);

    CategoryDTO toDTO(Category category);
}