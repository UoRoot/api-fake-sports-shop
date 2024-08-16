package es.diplock.examples.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import es.diplock.examples.dtos.product.ProductDTO;
import es.diplock.examples.dtos.product.SaveProductDTO;
import es.diplock.examples.entities.Brand;
import es.diplock.examples.entities.Category;
import es.diplock.examples.entities.Color;
import es.diplock.examples.entities.Product;
import es.diplock.examples.entities.SizeEntity;
import es.diplock.examples.enums.GenderEnum;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = { BrandMapper.class,
        CategoryMapper.class, SizeMapper.class,
        ColorMapper.class })
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "sizes", source = "sizesIds"),
            @Mapping(target = "colors", source = "colorsIds"),
            @Mapping(target = "category.id", source = "categoryId"),
            @Mapping(target = "brand.id", source = "brandId")
    })
    Product saveToEntity(SaveProductDTO saveProductDTO);

    @InheritInverseConfiguration(name = "saveToEntity")
    SaveProductDTO saveToDTO(Product product);

    @Mappings({
            @Mapping(target = "sizes", source = "sizesIds"),
            @Mapping(target = "colors", source = "colorsIds"),
            @Mapping(target = "category.id", source = "categoryId"),
            @Mapping(target = "brand.id", source = "brandId")
    })
    Product toEntity(ProductDTO productDTO);

    @InheritInverseConfiguration(name = "toEntity")
    ProductDTO toDTO(Product product);

    List<Product> toEntityList(List<ProductDTO> productDTOs);

    List<ProductDTO> toDTOList(List<Product> products);

    @Mapping(target = "sizes", ignore = true)
    @Mapping(target = "colors", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    void updateEntityFromDto(SaveProductDTO dto, @MappingTarget Product entity);

    default Category mapIdToCategory(Integer id) {
        if (id == null)
            return null;
        return Category.builder().id(id).build();
    }

    default Brand mapIdToBrand(Integer id) {
        if (id == null)
            return null;
        return Brand.builder().id(id).build();
    }

    default Set<SizeEntity> mapSizeIdsToSizes(List<Integer> ids) {
        if (ids == null)
            return null;
        return ids.stream()
                .map(id -> SizeEntity.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    default List<Integer> mapSizesToSizeIds(Set<SizeEntity> sizes) {
        if (sizes == null)
            return null;
        return sizes.stream()
                .map(size -> size.getId().intValue())
                .collect(Collectors.toList());
    }

    default Set<Color> mapColorIdsToColors(List<Integer> ids) {
        if (ids == null)
            return null;
        return ids.stream()
                .map(id -> Color.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    default List<Integer> mapColorsToColorIds(Set<Color> colors) {
        if (colors == null)
            return null;
        return colors.stream()
                .map(color -> color.getId().intValue())
                .collect(Collectors.toList());
    }

    default GenderEnum mapGenderStringToGenderEnum(String gender) {
        return GenderEnum.getEnum(gender);
    }

    default String mapGenderEnumToGenderString(GenderEnum gender) {
        return gender.getDescription();
    }
}