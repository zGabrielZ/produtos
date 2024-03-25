package br.com.gabrielferreira.produtos.api.mapper;

import br.com.gabrielferreira.produtos.api.dto.ItemPedidoDTO;
import br.com.gabrielferreira.produtos.api.dto.create.ItemPedidoCreateDTO;
import br.com.gabrielferreira.produtos.domain.model.ItemPedidoV2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface ItemPedidoMapper {

    @Mapping(target = "produto.id", source = "idProduto")
    ItemPedidoV2 toItemPedido(ItemPedidoCreateDTO itemPedidoCreateDTO);

    default List<ItemPedidoV2> toItensPedidos(List<ItemPedidoCreateDTO> itemPedidoCreateDTOS){
        return itemPedidoCreateDTOS.stream().map(this::toItemPedido).toList();
    }

    ItemPedidoDTO toItemPedidoDto(ItemPedidoV2 itemPedidoV2);

    default List<ItemPedidoDTO> toItensPedidosDtos(List<ItemPedidoV2> itemPedidoV2s){
        return itemPedidoV2s.stream().map(this::toItemPedidoDto).toList();
    }
}
