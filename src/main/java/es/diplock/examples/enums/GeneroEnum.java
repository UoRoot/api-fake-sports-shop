
package es.diplock.examples.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GeneroEnum {
    MASCULINO("Masculino"), FEMENINO("Femenino"), UNISEX("Unisex");

    private String descripcion;

    public static GeneroEnum getEnum(String descripcion) {
        switch (descripcion) {
            case "Masculino":
                return MASCULINO;
            case "Femenino":
                return FEMENINO;
            case "Unisex":
                return UNISEX;
            default:
                return null;
        }
    };
}