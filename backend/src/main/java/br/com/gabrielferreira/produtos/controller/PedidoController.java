package br.com.gabrielferreira.produtos.controller;

import java.net.URI;

import br.com.gabrielferreira.produtos.modelo.entidade.Pedido;
import br.com.gabrielferreira.produtos.modelo.entidade.dto.PedidoDTO;
import br.com.gabrielferreira.produtos.modelo.entidade.dto.PedidoNovoDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gabrielferreira.produtos.service.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<Pedido> cadastrar(@Valid @RequestBody PedidoNovoDTO pedidoNovoDTO){
		Pedido pedido = paraClasse(pedidoNovoDTO);
		pedido = pedidoService.inserir(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}/finalizar")
	public ResponseEntity<Void> finalizar(@PathVariable Long id){
		pedidoService.finalizarPedido(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PedidoDTO> buscarId(@PathVariable Long id){
		Pedido pedido = pedidoService.buscarId(id);
		return ResponseEntity.ok().body(paraDto(pedido));
	}
		
	public Pedido paraClasse(PedidoNovoDTO dto) {
		return modelMapper.map(dto,Pedido.class);
	}
	
	public PedidoDTO paraDto(Pedido pedido) {
		return modelMapper.map(pedido,PedidoDTO.class);
	}
	
}
