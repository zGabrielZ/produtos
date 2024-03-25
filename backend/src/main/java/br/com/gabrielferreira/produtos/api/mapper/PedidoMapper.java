package br.com.gabrielferreira.produtos.api.mapper;

import br.com.gabrielferreira.produtos.api.dto.PedidoDTO;
import br.com.gabrielferreira.produtos.api.dto.PedidoResumidoDTO;
import br.com.gabrielferreira.produtos.api.dto.create.PedidoCreateDTO;
import br.com.gabrielferreira.produtos.domain.model.PedidoV2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FormatMapper.class, ItemPedidoMapper.class, ClienteMapper.class})
public interface PedidoMapper {

    PedidoV2 toPedido(PedidoCreateDTO pedidoCreateDTO);

    @Mapping(target = "data", qualifiedByName = "formatData")
    @Mapping(target = "dataFinalizado", qualifiedByName = "formatData")
    @Mapping(target = "dataInclusao", qualifiedByName = "formatData")
    @Mapping(target = "dataAtualizacao", qualifiedByName = "formatData")
    PedidoDTO toPedidoDto(PedidoV2 pedidoV2);

    @Mapping(target = "data", qualifiedByName = "formatData")
    @Mapping(target = "dataFinalizado", qualifiedByName = "formatData")
    @Mapping(target = "dataInclusao", qualifiedByName = "formatData")
    @Mapping(target = "dataAtualizacao", qualifiedByName = "formatData")
    PedidoResumidoDTO toPedidoResumidoDto(PedidoV2 pedidoV2);
}
