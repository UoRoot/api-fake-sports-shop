package es.diplock.examples.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import es.diplock.examples.dtos.BrandDTO;
import es.diplock.examples.repositories.BrandRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BrandControlle {

    private final BrandRepository brandRepository;

    public ResponseEntity<List<BrandDTO>> getAllsBrand() {
        return ResponseEntity.ok(null);
    }

}
