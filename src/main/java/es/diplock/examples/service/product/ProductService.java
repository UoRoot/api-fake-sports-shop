package es.diplock.examples.service.product;

import es.diplock.examples.dtos.product.SaveProductDTO;

import org.springframework.data.domain.Page;

import es.diplock.examples.dtos.product.ProductDTO;
import es.diplock.examples.dtos.product.ProductSearchCriteriaDTO;

public interface ProductService {

    Page<ProductDTO> findAllProducts(ProductSearchCriteriaDTO criteria);

    ProductDTO findProductById(Long id);

    ProductDTO saveProduct(SaveProductDTO saveProductDTO);

    ProductDTO updateProduct(Long id, SaveProductDTO productDTO);

    void deleteProduct(Long id);

}
