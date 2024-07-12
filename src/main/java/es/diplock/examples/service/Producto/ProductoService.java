package es.diplock.examples.service.Producto;

import java.util.List;

import es.diplock.examples.dtos.ProductoDTO;

public interface ProductoService {

    List<ProductoDTO> findAll();
    ProductoDTO findById(Long id);
    ProductoDTO save(ProductoDTO productoDTO);
    void update(ProductoDTO productoDTO);
    void delete(Long id);

}
