package es.diplock.examples.validators.product;

import java.math.BigDecimal;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.diplock.examples.dtos.product.SaveProductDTO;

public class PartialProductValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return SaveProductDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SaveProductDTO productDTO = (SaveProductDTO) target;

        if (productDTO.getName() != null) {
            if (productDTO.getName().length() < 3 || productDTO.getName().length() > 100) {
                errors.rejectValue("name", "product.name.size",
                        "The product name must be between 3 and 100 characters");
            }
        }

        if (productDTO.getPrice() != null) {
            if (productDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                errors.rejectValue("price", "product.price.positive", "The price must be greater than 0");
            }
        }

        if (productDTO.getStockQuantity() != null) {
            if (productDTO.getStockQuantity() < 0 || productDTO.getStockQuantity() > 999) {
                errors.rejectValue("stockQuantity", "product.stock.range",
                        "The quantity in stock must be between 0 and 999");
            }
        }

    }
}
