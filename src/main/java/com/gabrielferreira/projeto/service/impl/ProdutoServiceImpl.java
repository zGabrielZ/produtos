package com.gabrielferreira.projeto.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabrielferreira.projeto.exceptions.ObjetoNaoEncotrado;
import com.gabrielferreira.projeto.modelo.entidade.Produto;
import com.gabrielferreira.projeto.repositorio.ProdutoRepositorio;
import com.gabrielferreira.projeto.service.ProdutoService;

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
