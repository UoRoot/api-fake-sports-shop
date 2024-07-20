package es.diplock.examples.service.Categoria;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.diplock.examples.dtos.CategoriaDTO;
import es.diplock.examples.entities.Categoria;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.mappers.CategoriaMapper;
import es.diplock.examples.repositories.CategoriaRepository;
import es.diplock.examples.service.BaseService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService implements BaseService<CategoriaDTO, Integer> {

    private final CategoriaRepository categoriaRepository;

    @Override
    public CategoriaDTO findById(Integer id) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);

        if (optionalCategoria.isPresent()) {
            return CategoriaMapper.toDto(optionalCategoria.get());
        }

        throw new ResourceNotFoundException("Producto no encontrado");
    }

    @Override
    public List<CategoriaDTO> findAll() {
        return null;
    }

    @Override
    public CategoriaDTO save(CategoriaDTO entity) {
        return null;
    }

    @Override
    public CategoriaDTO update(Integer id, CategoriaDTO entity) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
    }

}
