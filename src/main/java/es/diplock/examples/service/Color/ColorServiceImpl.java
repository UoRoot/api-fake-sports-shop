package es.diplock.examples.service.Color;

import org.springframework.stereotype.Service;

import es.diplock.examples.entities.Color;
import es.diplock.examples.exceptions.ResourceNotFoundException;
import es.diplock.examples.repositories.ColorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    public Color findById(Integer id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color not found"));
    }

}
