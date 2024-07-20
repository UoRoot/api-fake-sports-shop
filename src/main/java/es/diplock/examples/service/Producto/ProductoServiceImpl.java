package es.diplock.examples.service.Producto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import es.diplock.examples.dtos.producto.CreateProductoDTO;
import es.diplock.examples.dtos.producto.ProductoDTO;
import es.diplock.examples.entities.Categoria;
import es.diplock.examples.entities.Color;
import es.diplock.examples.entities.Producto;
import es.diplock.examples.entities.Talla;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.mappers.ProductoMapper;
import es.diplock.examples.repositories.CategoriaRepository;
import es.diplock.examples.repositories.ColorRepository;
import es.diplock.examples.repositories.ProductoRepository;
import es.diplock.examples.repositories.TallaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    private final ColorRepository colorRepository;

    private final TallaRepository tallaRepository;

    private final CategoriaRepository categoriaRepository;

    public List<ProductoDTO> findAllProducts() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(ProductoMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductoDTO findProductById(Long id) {
        return productoRepository.findById(id)
                .map(ProductoMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado."));
    }

    public ProductoDTO saveProduct(CreateProductoDTO productoDTO) {
        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        Set<Color> colores = new HashSet<>(colorRepository.findAllById(productoDTO.getColoresIds()));
        if (colores.size() != productoDTO.getColoresIds().size()) {
            throw new ResourceNotFoundException("Uno o más colores no encontrados");
        }

        Set<Talla> tallas = new HashSet<>(tallaRepository.findAllById(productoDTO.getTallasIds()));
        if (tallas.size() != productoDTO.getTallasIds().size()) {
            throw new ResourceNotFoundException("Una o más tallas no encontradas");
        }

        Producto producto = productoRepository.save(ProductoMapper.toEntity(productoDTO, categoria, colores, tallas));
        return ProductoMapper.toDto(producto);
    }

    public void updateProduct(ProductoDTO productoDTO) {
    };

    public void deleteProduct(Long id) {
    };

}
