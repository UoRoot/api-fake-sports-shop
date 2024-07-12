package es.diplock.examples.dtos;

import java.math.BigDecimal;
import java.util.Set;

public record ProductoDTO(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer cantidadStock,
        String talla,
        Set<ColorDTO> colores,
        String categoria) {
}
