package es.diplock.examples.mappers;

import es.diplock.examples.dtos.SizeDTO;
import es.diplock.examples.entities.SizeEntity;

public class SizeMapper {
    public static SizeDTO toDto(SizeEntity size) {
        if (size == null) {
            return null;
        }
        return new SizeDTO(size.getId(), size.getSize());
    }

    public static SizeEntity toEntity(SizeDTO sizeDTO) {
        if (sizeDTO == null) {
            return null;
        }
        SizeEntity size = new SizeEntity();
        size.setId(sizeDTO.id());
        size.setSize(sizeDTO.size());
        return size;
    }
}
