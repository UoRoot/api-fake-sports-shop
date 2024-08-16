package es.diplock.examples.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.diplock.examples.dtos.product.SaveProductDTO;
import es.diplock.examples.dtos.product.ProductDTO;
import es.diplock.examples.exceptions.NoContentException;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.service.product.ProductService;
import es.diplock.examples.validators.product.PartialProductValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    // private final PartialProductValidator partialProductValidator;

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
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody SaveProductDTO newProductDTO) {
        ProductDTO savedProduct = productService.saveProduct(newProductDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.id())
                .toUri();
        return ResponseEntity.created(location).body(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody SaveProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Producto eliminado con exito");
    }
    /* ESTE METODO ETÁ EN PROCESO */
    /* debo crear otro metodo para que no se confunda con el de arriba */
    // @PatchMapping("/products/{id}")
    // public ResponseEntity<?> partiallyUpdateProduct(
    // @PathVariable Long id,
    // @RequestBody SaveProductDTO productDTO,
    // BindingResult result) {

    // partialProductValidator.validate(productDTO, result);

    // if (result.hasErrors()) {
    // return ResponseEntity.badRequest().body(result.getAllErrors());
    // }

    // ProductDTO updatedProduct = productService.updateProduct(id, productDTO);

    // return ResponseEntity.ok(updatedProduct);
    // }

}
