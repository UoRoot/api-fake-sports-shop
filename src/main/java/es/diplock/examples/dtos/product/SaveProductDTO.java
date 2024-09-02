package es.diplock.examples.dtos.product;

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
public class SaveProductDTO {

    @NotBlank(message = "The product name is mandatory")
    @Size(min = 3, max = 100, message = "The product name must be between 3 and 100 characters")
    private String name;

    private String description;

    private String imageUrl;

    @NotNull(message = "The price is mandatory")
    @DecimalMin(value = "00", inclusive = false, message = "The price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "The price must have a maximum of 10 whole digits and 2 decimal places")
    private BigDecimal price;

    @NotNull(message = "The quantity in stock is mandatory")
    @Range(min = 0, max = 999, message = "The quantity in stock must be between 0 to 999")
    private Integer stockQuantity;

    @NotNull(message = "The gender must be 'Male', 'Female' or 'Unisex'")
    private String gender;

    @NotNull(message = "sizesIds is mandatory")
    @Size(min = 1, message = "You must include at least one ID")
    private List<Integer> sizesIds;

    @NotNull(message = "colorsIds is mandatory")
    @Size(min = 1, message = "You must include at least one ID")
    private List<Integer> colorsIds;

    @NotNull(message = "Subcategory ID is mandatory")
    private Integer subcategoryId;

    @NotNull(message = "Brand ID is mandatory")
    private Integer brandId;

}