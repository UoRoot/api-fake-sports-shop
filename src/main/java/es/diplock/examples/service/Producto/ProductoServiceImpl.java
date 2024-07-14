package es.diplock.examples.service.Producto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import es.diplock.examples.dtos.ProductoDTO;
import es.diplock.examples.entities.Categoria;
import es.diplock.examples.entities.Color;
import es.diplock.examples.entities.Producto;
import es.diplock.examples.entities.Talla;
import es.diplock.examples.mappers.ProductoMapper;
import es.diplock.examples.repositories.ProductoRepository;
import es.diplock.examples.service.Categoria.CategoriaService;
import es.diplock.examples.service.Color.ColorService;
import es.diplock.examples.service.Talla.TallaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    private final ColorService colorService;

    private final TallaService tallaService;

    private final CategoriaService categoriaService;

    public List<ProductoDTO> findAll() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(ProductoMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductoDTO findById(Long id) {
        return productoRepository.findById(id)
                .map(ProductoMapper::toDto)
                .orElse(null);
    }

    public ProductoDTO save(ProductoDTO productoDTO) {
        Categoria categoria = categoriaService.findById(productoDTO.categoria());
        Set<Color> colores = productoDTO.colores().stream()
                .map(colorService::findById)
                .collect(Collectors.toSet());

        Set<Talla> tallas = productoDTO.tallas().stream()
                .map(tallaService::findById)
                .collect(Collectors.toSet());

        Producto producto = productoRepository.save(ProductoMapper.toEntity(productoDTO, categoria, colores, tallas));

        return ProductoMapper.toDto(producto);
    };

    public void update(ProductoDTO productoDTO) {
    };

    public void delete(Long id) {
    };

}
