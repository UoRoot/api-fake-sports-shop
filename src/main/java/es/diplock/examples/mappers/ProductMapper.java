package es.diplock.examples.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import es.diplock.examples.dtos.product.CreateProductDTO;
import es.diplock.examples.dtos.product.ProductDTO;
import es.diplock.examples.entities.Brand;
import es.diplock.examples.entities.Category;
import es.diplock.examples.entities.Color;
import es.diplock.examples.entities.Product;
import es.diplock.examples.entities.SizeEntity;
import es.diplock.examples.enums.GenderEnum;

public class ProductMapper {

    public static ProductDTO toDto(Product producto) {
        if (producto == null) {
            return null;
        }

        Set<Integer> colores = producto.getColors().stream()
                .map(color -> color.getId())
                .collect(Collectors.toSet());

        Set<Integer> tallas = producto.getSizes().stream()
                .map(talla -> talla.getId())
                .collect(Collectors.toSet());

        return new ProductDTO(
                producto.getId(),
                producto.getName(),
                producto.getDescription(),
                producto.getPrice(),
                producto.getStockQuantity(),
                producto.getGender().getDescription(),
                tallas,
                colores,
                producto.getCategory().getId(),
                producto.getBrand().getId());
    }

    public static Product toEntity(CreateProductDTO productoDTO, Category categoria, Brand brand, Set<Color> colores,
            Set<SizeEntity> tallas) {
        if (productoDTO == null) {
            return null;
        }

        Product producto = new Product();
        producto.setName(productoDTO.getName());
        producto.setDescription(productoDTO.getDescription());
        producto.setPrice(productoDTO.getPrice());
        producto.setStockQuantity(productoDTO.getStockQuantity());
        producto.setGender(GenderEnum.getEnum(productoDTO.getGender()));
        producto.setSizes(tallas);
        producto.setColors(colores);
        producto.setCategory(categoria);
        producto.setBrand(brand);

        return producto;
    }

}
