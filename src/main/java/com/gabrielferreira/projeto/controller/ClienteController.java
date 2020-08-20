package com.gabrielferreira.projeto.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gabrielferreira.projeto.modelo.entidade.Cliente;
import com.gabrielferreira.projeto.modelo.entidade.dto.ClienteDTO;
import com.gabrielferreira.projeto.modelo.entidade.dto.ClienteNovoDTO;
import com.gabrielferreira.projeto.service.impl.ClienteServiceImpl;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteServiceImpl clienteService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<Cliente> cadastrar(@Valid @RequestBody ClienteNovoDTO clienteNovoDTO){
		Cliente cliente = paraClasse(clienteNovoDTO);
		cliente = clienteService.inserir(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> listagem(){
		List<Cliente> clientes = clienteService.listagem();
		return ResponseEntity.ok().body(colecaoClasse(clientes));
	}
	
	public Cliente paraClasse(ClienteNovoDTO dto) {
		return modelMapper.map(dto,Cliente.class);
	}
	
	public ClienteDTO paraDto(Cliente cliente) {
		return modelMapper.map(cliente,ClienteDTO.class);
	}
	
	private List<ClienteDTO> colecaoClasse(List<Cliente> clientes) {
		return clientes.stream()
				.map(cliente -> paraDto(cliente))
				.collect(Collectors.toList());
	}
}
