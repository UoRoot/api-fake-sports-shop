package es.diplock.examples.service.product;

import java.util.List;

import es.diplock.examples.dtos.product.CreateProductDTO;
import es.diplock.examples.dtos.product.ProductDTO;

public interface ProductService {

    List<ProductDTO> findAllProducts();

    ProductDTO findProductById(Long id);

    ProductDTO saveProduct(CreateProductDTO createProductDTO);

    void updateProduct(ProductDTO productDTO);

    void deleteProduct(Long id);

}
