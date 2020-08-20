package com.gabrielferreira.projeto.service;


import com.gabrielferreira.projeto.modelo.entidade.Pedido;

public interface PedidoService {

	public Pedido inserir(Pedido pedido);
	
	public Pedido buscarId(Long id);
	
	public void finalizarPedido(Long id);

}
