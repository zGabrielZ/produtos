package br.com.gabrielferreira.produtos.service;


import br.com.gabrielferreira.produtos.modelo.entidade.Pedido;

public interface PedidoService {

	public Pedido inserir(Pedido pedido);
	
	public Pedido buscarId(Long id);
	
	public void finalizarPedido(Long id);

}
