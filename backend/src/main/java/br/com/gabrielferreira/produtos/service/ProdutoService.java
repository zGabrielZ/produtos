package br.com.gabrielferreira.produtos.service;

import java.util.List;

import br.com.gabrielferreira.produtos.modelo.entidade.Produto;

public interface ProdutoService {

	public Produto inserir(Produto produto);
	
	public List<Produto> listagem();
	
	public Produto procurarId(Long id);
}
