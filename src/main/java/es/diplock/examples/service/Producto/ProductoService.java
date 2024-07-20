package es.diplock.examples.service.Producto;

import java.util.List;

import es.diplock.examples.dtos.producto.CreateProductoDTO;
import es.diplock.examples.dtos.producto.ProductoDTO;

public interface ProductoService {

    List<ProductoDTO> findAllProducts();

    ProductoDTO findProductById(Long id);

    ProductoDTO saveProduct(CreateProductoDTO createProductoDTO);

    void updateProduct(ProductoDTO productoDTO);

    void deleteProduct(Long id);

}
