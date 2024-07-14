package es.diplock.examples.service.Talla;

import org.springframework.stereotype.Service;

import es.diplock.examples.entities.Talla;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.repositories.TallaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TallaServiceImpl implements TallaService {

    private final TallaRepository tallaRepository;

    public Talla findById(Integer id) {
        return tallaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talla no encontrada"));
    }

}
