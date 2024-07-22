package es.diplock.examples.service.size;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.diplock.examples.dtos.SizeDTO;
import es.diplock.examples.entities.SizeEntity;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.mappers.SizeMapper;
import es.diplock.examples.repositories.SizeRepository;
import es.diplock.examples.service.BaseService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SizeService implements BaseService<SizeDTO, Integer> {

    private final SizeRepository sizeRepository;

    public SizeDTO findById(Integer id) {
        Optional<SizeEntity> optionalSize = sizeRepository.findById(id);

        if (optionalSize.isPresent()) {
            return SizeMapper.toDto(optionalSize.get());
        }

        throw new ResourceNotFoundException("Producto no encontrado");
    }

    @Override
    public List<SizeDTO> findAll() {
        return null;
    }

    @Override
    public SizeDTO save(SizeDTO entity) {
        return null;
    }

    @Override
    public SizeDTO update(Integer id, SizeDTO entity) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
    }

}
