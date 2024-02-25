package br.com.gabrielferreira.produtos.service;

import java.util.List;

import br.com.gabrielferreira.produtos.modelo.entidade.Cliente;

public interface ClienteService {

	public Cliente inserir(Cliente cliente);
	
	public Cliente procurarId(Long id);
	
	public void validarEmail(String email);

	public List<Cliente> listagem();
}
