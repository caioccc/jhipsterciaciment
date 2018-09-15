package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.PedidoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pedido and its DTO PedidoDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, VendedorMapper.class})
public interface PedidoMapper extends EntityMapper<PedidoDTO, Pedido> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "vendedor.id", target = "vendedorId")
    PedidoDTO toDto(Pedido pedido);

    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "vendedorId", target = "vendedor")
    @Mapping(target = "items", ignore = true)
    Pedido toEntity(PedidoDTO pedidoDTO);

    default Pedido fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pedido pedido = new Pedido();
        pedido.setId(id);
        return pedido;
    }
}
