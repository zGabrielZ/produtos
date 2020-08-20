package com.gabrielferreira.projeto.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.gabrielferreira.projeto.modelo.entidade.Produto;
import com.gabrielferreira.projeto.modelo.entidade.dto.ProdutoDTO;
import com.gabrielferreira.projeto.modelo.entidade.dto.ProdutoNovoDTO;
import com.gabrielferreira.projeto.service.impl.ProdutoServiceImpl;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoServiceImpl produtoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<Produto> cadastrar(@Valid @RequestBody ProdutoNovoDTO produtoNovoDTO){
		Produto produto = paraClasse(produtoNovoDTO);
		produto = produtoService.inserir(produto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(produto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> listagem(){
		List<Produto> produtos = produtoService.listagem();
		return ResponseEntity.ok().body(colecaoClasse(produtos));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDTO> buscarId(@PathVariable Long id){
		Produto produto = produtoService.procurarId(id);
		return ResponseEntity.ok().body(paraDto(produto));
	}
	
	public Produto paraClasse(ProdutoNovoDTO dto) {
		return modelMapper.map(dto,Produto.class);
	}
	
	public ProdutoDTO paraDto(Produto produto) {
		return modelMapper.map(produto,ProdutoDTO.class);
	}
	
	private List<ProdutoDTO> colecaoClasse(List<Produto> produtos) {
		return produtos.stream()
				.map(produto -> paraDto(produto))
				.collect(Collectors.toList());
	}
}
