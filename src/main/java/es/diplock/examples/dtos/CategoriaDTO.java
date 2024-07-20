package es.diplock.examples.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaDTO(
                Integer id,
                @NotBlank(message = "campo requerido") @Size(min = 3, max = 50, message = "nombre de la categoria debe tener entre 3 a 50 caracteres") String nombre) {
}