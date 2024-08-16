package es.diplock.examples.service.product;

import java.util.List;

import es.diplock.examples.dtos.product.SaveProductDTO;
import es.diplock.examples.dtos.product.ProductDTO;

public interface ProductService {

    List<ProductDTO> findAllProducts();

    ProductDTO findProductById(Long id);

    ProductDTO saveProduct(SaveProductDTO saveProductDTO);

    ProductDTO updateProduct(Long id, SaveProductDTO productDTO);

    void deleteProduct(Long id);

}
