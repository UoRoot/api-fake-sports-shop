package es.diplock.examples.service.Producto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import es.diplock.examples.dtos.ProductoDTO;
import es.diplock.examples.entities.Producto;
import es.diplock.examples.mappers.ProductoMapper;
import es.diplock.examples.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

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
        return null;
    };

    public void update(ProductoDTO productoDTO) {
    };

    public void delete(Long id) {
    };

}
