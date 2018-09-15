package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.EntradaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Entrada and its DTO EntradaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntradaMapper extends EntityMapper<EntradaDTO, Entrada> {



    default Entrada fromId(Long id) {
        if (id == null) {
            return null;
        }
        Entrada entrada = new Entrada();
        entrada.setId(id);
        return entrada;
    }
}
