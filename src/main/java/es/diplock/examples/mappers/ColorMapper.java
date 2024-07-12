package es.diplock.examples.mappers;

import es.diplock.examples.dtos.ColorDTO;
import es.diplock.examples.entities.Color;

public class ColorMapper {

    public static ColorDTO toDto(Color color) {
        if (color == null) {
            return null;
        }
        return new ColorDTO(color.getId(), color.getColor());
    }

    public static Color toEntity(ColorDTO colorDTO) {
        if (colorDTO == null) {
            return null;
        }
        Color color = new Color();
        color.setId(colorDTO.id());
        color.setColor(colorDTO.color());
        return color;
    }
}
