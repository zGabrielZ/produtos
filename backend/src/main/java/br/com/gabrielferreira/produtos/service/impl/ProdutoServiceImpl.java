package br.com.gabrielferreira.produtos.service.impl;

import java.util.List;
import java.util.Optional;

import br.com.gabrielferreira.produtos.modelo.entidade.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabrielferreira.produtos.exceptions.ObjetoNaoEncotrado;
import br.com.gabrielferreira.produtos.repositorio.ProdutoRepositorio;
import br.com.gabrielferreira.produtos.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@Override
	public Produto inserir(Produto produto) {
		return produtoRepositorio.save(produto);
	}

	@Override
	public List<Produto> listagem() {
		return produtoRepositorio.findAll();
	}


	@Override
	public Produto procurarId(Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		return produto.orElseThrow(() -> new ObjetoNaoEncotrado("Produto não encontrado"));
	}


	

}
