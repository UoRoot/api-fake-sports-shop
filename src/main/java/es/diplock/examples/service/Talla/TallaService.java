package es.diplock.examples.service.Talla;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.diplock.examples.dtos.TallaDTO;
import es.diplock.examples.entities.Talla;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.mappers.TallaMapper;
import es.diplock.examples.repositories.TallaRepository;
import es.diplock.examples.service.BaseService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TallaService implements BaseService<TallaDTO, Integer> {

    private final TallaRepository tallaRepository;

    public TallaDTO findById(Integer id) {
        Optional<Talla> optionalTalla = tallaRepository.findById(id);

        if (optionalTalla.isPresent()) {
            return TallaMapper.toDto(optionalTalla.get());
        }

        throw new ResourceNotFoundException("Producto no encontrado");
    }

    @Override
    public List<TallaDTO> findAll() {
        return null;
    }

    @Override
    public TallaDTO save(TallaDTO entity) {
        return null;
    }

    @Override
    public TallaDTO update(Integer id, TallaDTO entity) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
    }

}
