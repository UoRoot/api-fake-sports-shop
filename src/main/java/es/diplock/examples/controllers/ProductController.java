package es.diplock.examples.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.diplock.examples.dtos.product.CreateProductDTO;
import es.diplock.examples.dtos.product.ProductDTO;
import es.diplock.examples.exceptions.NoContentException;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> productDTOs = productService.findAllProducts();
        if (productDTOs.isEmpty()) {
            throw new NoContentException("There are no products available");
        }
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.findProductById(id);
        if (productDTO == null) {
            throw new ResourceNotFoundException("Product not found");
        }
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody CreateProductDTO newProductDTO) {
        ProductDTO savedProduct = productService.saveProduct(newProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
}
