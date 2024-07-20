package es.diplock.examples.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.diplock.examples.dtos.producto.CreateProductoDTO;
import es.diplock.examples.dtos.producto.ProductoDTO;
import es.diplock.examples.service.Producto.ProductoService;
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
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getProducts() {
        List<ProductoDTO> productoDTOs = productoService.findAllProducts();
        if (productoDTOs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductById(@PathVariable Long id) {
        ProductoDTO productoDTO = productoService.findProductById(id);
        if (productoDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoDTO);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> createProduct(@Valid @RequestBody CreateProductoDTO newProductoDTO) {
        ProductoDTO savedProducto = productoService.saveProduct(newProductoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProducto);
    }
}
