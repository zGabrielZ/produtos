package com.gabrielferreira.projeto.service.impl;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabrielferreira.projeto.exceptions.ObjetoNaoEncotrado;
import com.gabrielferreira.projeto.modelo.entidade.Cliente;
import com.gabrielferreira.projeto.modelo.entidade.Itens;
import com.gabrielferreira.projeto.modelo.entidade.Pedido;
import com.gabrielferreira.projeto.modelo.entidade.enums.PedidoStatus;
import com.gabrielferreira.projeto.repositorio.ItensRepositorio;
import com.gabrielferreira.projeto.repositorio.PedidoRepositorio;
import com.gabrielferreira.projeto.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService{

	@Autowired
	private PedidoRepositorio pedidoRepositorio;
	
	@Autowired
	private ClienteServiceImpl clienteService;
	
	@Autowired
	private ProdutoServiceImpl produtoService;
	
	@Autowired
	private ItensRepositorio itensRepositorio;
	
	@Override
	public Pedido inserir(Pedido pedido) {
		Cliente cliente = clienteService.procurarId(pedido.getCliente().getId());
		pedido.setCliente(cliente);
		cliente.getPedidos().add(pedido);
		pedido.setPedidoStatus(PedidoStatus.ABERTO);
		pedido.setDataDoPedido(LocalDateTime.now());
		pedido = pedidoRepositorio.save(pedido);
		for(Itens i : pedido.getItens()) {
			i.setProduto(produtoService.procurarId(i.getProduto().getId()));
			i.setPreco(i.getProduto().getPreco());
			i.setPedido(pedido);
		}
		
		itensRepositorio.saveAll(pedido.getItens());
		
		return pedido;
	}

	@Override
	public Pedido buscarId(Long id) {
		Optional<Pedido> pedido = pedidoRepositorio.findById(id);
		return pedido.orElseThrow(() -> new ObjetoNaoEncotrado("Pedido não encontrado"));
	}

	@Override
	public void finalizarPedido(Long id) {
		Pedido pedido = buscarId(id);
		pedido.finalizarPedido();
		pedidoRepositorio.save(pedido);
	}

}
