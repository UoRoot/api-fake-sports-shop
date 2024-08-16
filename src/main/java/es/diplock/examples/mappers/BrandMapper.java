package es.diplock.examples.mappers;

import org.mapstruct.Mapper;

import es.diplock.examples.dtos.BrandDTO;
import es.diplock.examples.entities.Brand;

@Mapper
public interface BrandMapper {

    Brand toEntity(BrandDTO brandDTO);

    BrandDTO toDTO(Brand brand);
}
