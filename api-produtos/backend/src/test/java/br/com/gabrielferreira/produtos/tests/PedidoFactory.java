package br.com.gabrielferreira.produtos.tests;

import br.com.gabrielferreira.produtos.api.dto.create.ItemPedidoCreateDTO;
import br.com.gabrielferreira.produtos.api.dto.create.PedidoCreateDTO;

import java.util.ArrayList;
import java.util.List;

public class PedidoFactory {

    private PedidoFactory(){}

    public static PedidoCreateDTO criarPedido(){
        List<ItemPedidoCreateDTO> itens = new ArrayList<>();
        itens.add(new ItemPedidoCreateDTO(2, 1L));
        itens.add(new ItemPedidoCreateDTO(3, 1L));
        itens.add(new ItemPedidoCreateDTO(5, 2L));

        return PedidoCreateDTO.builder()
                .itensPedidos(itens)
                .build();
    }
}
