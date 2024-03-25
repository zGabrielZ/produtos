package br.com.gabrielferreira.produtos.domain.service.impl;

import br.com.gabrielferreira.produtos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.produtos.domain.model.ClienteV2;
import br.com.gabrielferreira.produtos.domain.model.ItemPedidoV2;
import br.com.gabrielferreira.produtos.domain.model.PedidoV2;
import br.com.gabrielferreira.produtos.domain.model.ProdutoV2;
import br.com.gabrielferreira.produtos.domain.model.enums.PedidoStatusEnum;
import br.com.gabrielferreira.produtos.domain.repository.PedidoV2Repository;
import br.com.gabrielferreira.produtos.domain.service.ClienteV2Service;
import br.com.gabrielferreira.produtos.domain.service.PedidoV2Service;
import br.com.gabrielferreira.produtos.domain.service.ProdutoV2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static br.com.gabrielferreira.produtos.common.utils.DataUtils.*;

@Service
@RequiredArgsConstructor
public class PedidoV2ServiceImpl implements PedidoV2Service {

    private final PedidoV2Repository pedidoV2Repository;

    private final ClienteV2Service clienteV2Service;

    private final ProdutoV2Service produtoV2Service;

    @Transactional
    @Override
    public PedidoV2 salvarPedido(Long idCliente, PedidoV2 pedidoV2) {
        ClienteV2 cliente = clienteV2Service.buscarClientePorId(idCliente);
        validarItemPedidoDuplicado(pedidoV2.getItensPedidos());

        for (ItemPedidoV2 itemPedido : pedidoV2.getItensPedidos()) {
            ProdutoV2 produto = produtoV2Service.buscarProdutoPorId(itemPedido.getProduto().getId());
            itemPedido.setPedido(pedidoV2);
            itemPedido.setProduto(produto);
            itemPedido.setPreco(produto.getPreco().multiply(BigDecimal.valueOf(itemPedido.getQuantidade())));
        }

        pedidoV2.setData(ZonedDateTime.now(UTC));
        pedidoV2.setPedidoStatus(PedidoStatusEnum.ABERTO);
        pedidoV2.setCliente(cliente);

        pedidoV2 = pedidoV2Repository.save(pedidoV2);
        return pedidoV2;
    }

    private void validarItemPedidoDuplicado(List<ItemPedidoV2> itemPedidoV2s){
        List<Long> idsProdutos = itemPedidoV2s.stream().map(i -> i.getProduto().getId()).toList();
        idsProdutos.forEach(idProduto -> {
            int duplicados = Collections.frequency(idsProdutos, idProduto);

            if(duplicados > 1){
                throw new RegraDeNegocioException("Não vai ser possível cadastrar este pedido pois tem produtos duplicados ou mais de duplicados");
            }
        });
    }
}
