package es.diplock.examples.mappers;

import org.mapstruct.Mapper;

import es.diplock.examples.dtos.SubcategoryDTO;
import es.diplock.examples.entities.Subcategory;

@Mapper
public interface SubcategoryMapper {
    Subcategory toEntity(SubcategoryDTO subcategoryDTO);
    SubcategoryDTO toDTO(Subcategory subcategory);
}
