package es.diplock.examples.mappers;

import es.diplock.examples.dtos.CategoriaDTO;
import es.diplock.examples.entities.Categoria;

public class CategoriaMapper {
    public static CategoriaDTO toDto(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        return new CategoriaDTO(categoria.getId(), categoria.getNombre());
    }

    public static Categoria toEntity(CategoriaDTO categoriaDTO) {
        if (categoriaDTO == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDTO.id());
        categoria.setNombre(categoriaDTO.nombre());
        return categoria;
    }
}