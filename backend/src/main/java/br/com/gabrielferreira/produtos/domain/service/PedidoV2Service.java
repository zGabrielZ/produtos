package br.com.gabrielferreira.produtos.domain.service;

import br.com.gabrielferreira.produtos.domain.model.PedidoV2;

public interface PedidoV2Service {

    PedidoV2 salvarPedido(Long idCliente, PedidoV2 pedidoV2);

    PedidoV2 buscarPedidoPorId(Long idCliente, Long idPedido);
}
