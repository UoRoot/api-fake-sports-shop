package es.diplock.examples.mappers;

import org.mapstruct.Mapper;

import es.diplock.examples.dtos.SizeDTO;
import es.diplock.examples.entities.SizeEntity;

@Mapper
public interface SizeMapper {

    SizeEntity toEntity(SizeDTO sizeDTO);

    SizeDTO toDTO(SizeEntity size);
}