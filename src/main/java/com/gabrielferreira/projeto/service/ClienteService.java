package com.gabrielferreira.projeto.service;

import java.util.List;

import com.gabrielferreira.projeto.modelo.entidade.Cliente;

public interface ClienteService {

	public Cliente inserir(Cliente cliente);
	
	public Cliente procurarId(Long id);
	
	public void validarEmail(String email);

	public List<Cliente> listagem();
}
