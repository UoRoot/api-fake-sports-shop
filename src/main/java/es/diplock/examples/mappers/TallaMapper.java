package es.diplock.examples.mappers;

import es.diplock.examples.dtos.TallaDTO;
import es.diplock.examples.entities.Talla;

public class TallaMapper {
    public static TallaDTO toDto(Talla talla) {
        if (talla == null) {
            return null;
        }
        return new TallaDTO(talla.getId(), talla.getTalla());
    }

    public static Talla toEntity(TallaDTO tallaDTO) {
        if (tallaDTO == null) {
            return null;
        }
        Talla talla = new Talla();
        talla.setId(tallaDTO.id());
        talla.setTalla(tallaDTO.talla());
        return talla;
    }
}
