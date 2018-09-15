package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.VendedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Vendedor and its DTO VendedorDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface VendedorMapper extends EntityMapper<VendedorDTO, Vendedor> {



    default Vendedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vendedor vendedor = new Vendedor();
        vendedor.setId(id);
        return vendedor;
    }
}
