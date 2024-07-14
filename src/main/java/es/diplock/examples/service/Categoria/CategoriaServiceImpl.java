package es.diplock.examples.service.Categoria;

import org.springframework.stereotype.Service;

import es.diplock.examples.entities.Categoria;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository colorRepository;

    public Categoria findById(Integer id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));
    }

}
