package es.diplock.examples.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.diplock.examples.dtos.ProductoDTO;
import es.diplock.examples.service.Producto.ProductoService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getProducts() {
        List<ProductoDTO> productoDTOs = productoService.findAll();
        if (productoDTOs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productoDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductById(@PathVariable Long id) {
        ProductoDTO productoDTO = productoService.findById(id);
        if (productoDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoDTO);
    }
}
