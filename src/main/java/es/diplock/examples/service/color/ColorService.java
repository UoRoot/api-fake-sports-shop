package es.diplock.examples.service.color;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.diplock.examples.dtos.ColorDTO;
import es.diplock.examples.entities.Color;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.mappers.ColorMapper;
import es.diplock.examples.repositories.ColorRepository;
import es.diplock.examples.service.BaseService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColorService implements BaseService<ColorDTO, Integer> {

    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    @Override
    public ColorDTO findById(Integer id) {
        Optional<Color> optionalColor = colorRepository.findById(id);

        if (optionalColor.isPresent()) {
            return colorMapper.toDTO(optionalColor.get());
        }

        throw new ResourceNotFoundException("Color no encontrado");
    }

    @Override
    public List<ColorDTO> findAll() {
        return null;
    }

    @Override
    public ColorDTO save(ColorDTO entity) {
        return null;
    }

    @Override
    public ColorDTO update(Integer id, ColorDTO entity) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
    }

}
