package es.diplock.examples.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import es.diplock.examples.dtos.ColorDTO;
import es.diplock.examples.dtos.ProductoDTO;
import es.diplock.examples.entities.Color;
import es.diplock.examples.entities.Producto;

public class ProductoMapper {

    public static ProductoDTO toDto(Producto producto) {
        if (producto == null) {
            return null;
        }

        // Set<ColorDTO> colores = producto.getColores().stream()
        // .map(ColorMapper::toDto)
        // .collect(Collectors.toSet());

        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadStock(),
                producto.getTalla(),
                null,
                producto.getCategoria());
    }

    public static Producto toEntity(ProductoDTO productoDTO) {
        if (productoDTO == null) {
            return null;
        }

        Producto producto = new Producto();
        producto.setId(productoDTO.id());
        producto.setNombre(productoDTO.nombre());
        producto.setDescripcion(productoDTO.descripcion());
        producto.setPrecio(productoDTO.precio());
        producto.setCantidadStock(productoDTO.cantidadStock());
        producto.setTalla(productoDTO.talla());
        producto.setCategoria(productoDTO.categoria());

        Set<Color> colores = productoDTO.colores().stream()
                .map(ColorMapper::toEntity)
                .collect(Collectors.toSet());

        producto.setColores(colores);

        return producto;
    }
}
