package es.diplock.examples.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import es.diplock.examples.dtos.ProductoDTO;
import es.diplock.examples.entities.Categoria;
import es.diplock.examples.entities.Color;
import es.diplock.examples.entities.Producto;
import es.diplock.examples.entities.Talla;
import es.diplock.examples.enums.GeneroEnum;

public class ProductoMapper {

    public static ProductoDTO toDto(Producto producto) {
        if (producto == null) {
            return null;
        }

        Set<Integer> colores = producto.getColores().stream()
                .map(color -> color.getId())
                .collect(Collectors.toSet());

        Set<Integer> tallas = producto.getTallas().stream()
                .map(talla -> talla.getId())
                .collect(Collectors.toSet());

        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadStock(),
                producto.getGenero().getDescripcion(),
                tallas,
                colores,
                producto.getCategoria().getId());
    }

    public static Producto toEntity(ProductoDTO productoDTO, Categoria categoria, Set<Color> colores,
            Set<Talla> tallas) {
        if (productoDTO == null) {
            return null;
        }

        Producto producto = new Producto();
        producto.setId(productoDTO.id());
        producto.setNombre(productoDTO.nombre());
        producto.setDescripcion(productoDTO.descripcion());
        producto.setPrecio(productoDTO.precio());
        producto.setCantidadStock(productoDTO.cantidadStock());
        producto.setGenero(GeneroEnum.getEnum(productoDTO.genero()));
        producto.setTallas(tallas);
        producto.setColores(colores);
        producto.setCategoria(categoria);

        return producto;
    }
}
