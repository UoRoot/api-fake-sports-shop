package es.diplock.examples.mappers;

import org.mapstruct.Mapper;

import es.diplock.examples.dtos.ColorDTO;
import es.diplock.examples.entities.Color;

@Mapper
public interface ColorMapper {
    Color toEntity(ColorDTO colorDTO);

    ColorDTO toDTO(Color color);

}