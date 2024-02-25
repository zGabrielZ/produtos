package br.com.gabrielferreira.produtos.service.impl;
import java.time.LocalDateTime;
import java.util.Optional;

import br.com.gabrielferreira.produtos.modelo.entidade.Cliente;
import br.com.gabrielferreira.produtos.modelo.entidade.Itens;
import br.com.gabrielferreira.produtos.modelo.entidade.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabrielferreira.produtos.exceptions.ObjetoNaoEncotrado;
import br.com.gabrielferreira.produtos.modelo.entidade.enums.PedidoStatus;
import br.com.gabrielferreira.produtos.repositorio.ItensRepositorio;
import br.com.gabrielferreira.produtos.repositorio.PedidoRepositorio;
import br.com.gabrielferreira.produtos.service.PedidoService;

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
