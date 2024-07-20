package es.diplock.examples.dtos.producto;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductoDTO {

    @NotBlank(message = "Campo requerido")
    @Size(min = 3, max = 100, message = "El nombre del producto debe tener entre 3 y 100 caracteres")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener un máximo de 10 dígitos enteros y 2 decimales")
    private BigDecimal precio;

    @NotNull(message = "La cantidad en stock es obligatoria")
    @Range(min = 0, max = 999, message = "La cantidad en stock debe ser entre 0 a 999")
    private Integer cantidadStock;

    @NotNull(message = "El género debe ser 'Masculino', 'Femenino' o 'Unisex'")
    private String genero;

    @NotNull(message = "Debes incluiur al menos un ID")
    private List<Integer> tallasIds;

    @NotNull(message = "Debes incluiur al menos un ID")
    private List<Integer> coloresIds;

    @NotNull(message = "El ID de categoria es obligatorio")
    private Integer categoriaId;

}
