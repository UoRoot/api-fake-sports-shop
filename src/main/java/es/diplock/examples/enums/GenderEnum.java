
package es.diplock.examples.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenderEnum {
    MALE("Male"), FEMALE("Female"), UNISEX("Unisex");

    private String description;

    public static GenderEnum getEnum(String description) {
        switch (description) {
            case "Male":
                return MALE;
            case "Female":
                return FEMALE;
            case "Unisex":
                return UNISEX;
            default:
                return null;
        }
    };
}