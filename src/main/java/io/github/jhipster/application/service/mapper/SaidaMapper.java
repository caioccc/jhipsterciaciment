package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.SaidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Saida and its DTO SaidaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SaidaMapper extends EntityMapper<SaidaDTO, Saida> {



    default Saida fromId(Long id) {
        if (id == null) {
            return null;
        }
        Saida saida = new Saida();
        saida.setId(id);
        return saida;
    }
}
