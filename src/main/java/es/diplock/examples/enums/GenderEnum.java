
package es.diplock.examples.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenderEnum {
    MALE("male"), FEMALE("female"), UNISEX("unisex");

    private String description;

    public static GenderEnum getEnum(String description) {
        switch (description) {
            case "male":
                return MALE;
            case "female":
                return FEMALE;
            case "unisex":
                return UNISEX;
            default:
                return null;
        }
    };
}