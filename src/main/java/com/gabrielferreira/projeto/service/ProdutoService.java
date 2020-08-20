package com.gabrielferreira.projeto.service;

import java.util.List;

import com.gabrielferreira.projeto.modelo.entidade.Produto;

public interface ProdutoService {

	public Produto inserir(Produto produto);
	
	public List<Produto> listagem();
	
	public Produto procurarId(Long id);
}
