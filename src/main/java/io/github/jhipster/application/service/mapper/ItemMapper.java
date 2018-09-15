package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Item and its DTO ItemDTO.
 */
@Mapper(componentModel = "spring", uses = {PedidoMapper.class, ProdutoMapper.class})
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {

    @Mapping(source = "pedido.id", target = "pedidoId")
    @Mapping(source = "produto.id", target = "produtoId")
    ItemDTO toDto(Item item);

    @Mapping(source = "pedidoId", target = "pedido")
    @Mapping(source = "produtoId", target = "produto")
    Item toEntity(ItemDTO itemDTO);

    default Item fromId(Long id) {
        if (id == null) {
            return null;
        }
        Item item = new Item();
        item.setId(id);
        return item;
    }
}
