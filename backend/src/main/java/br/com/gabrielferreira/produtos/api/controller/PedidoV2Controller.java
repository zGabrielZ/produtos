package br.com.gabrielferreira.produtos.api.controller;

import br.com.gabrielferreira.produtos.api.dto.PedidoDTO;
import br.com.gabrielferreira.produtos.api.dto.PedidoResumidoDTO;
import br.com.gabrielferreira.produtos.api.dto.create.PedidoCreateDTO;
import br.com.gabrielferreira.produtos.api.mapper.PedidoMapper;
import br.com.gabrielferreira.produtos.domain.model.PedidoV2;
import br.com.gabrielferreira.produtos.domain.service.PedidoV2Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v2/clientes/{idCliente}/pedidos")
@RequiredArgsConstructor
public class PedidoV2Controller {

    private final PedidoV2Service pedidoV2Service;

    private final PedidoMapper pedidoMapper;

    @PostMapping
    public ResponseEntity<PedidoDTO> cadastrarPedido(@PathVariable Long idCliente, @Valid @RequestBody PedidoCreateDTO pedidoCreateDTO){
        PedidoV2 pedido = pedidoMapper.toPedido(pedidoCreateDTO);
        PedidoV2 pedidoCadastrado = pedidoV2Service.salvarPedido(idCliente, pedido);
        PedidoDTO pedidoDTO = pedidoMapper.toPedidoDto(pedidoCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(pedidoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(pedidoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResumidoDTO> buscarPedidoPorId(@PathVariable Long idCliente, @PathVariable Long id){
        PedidoV2 pedido = pedidoV2Service.buscarPedidoPorId(idCliente, id);
        PedidoResumidoDTO pedidoResumidoDTO = pedidoMapper.toPedidoResumidoDto(pedido);

        return ResponseEntity.ok().body(pedidoResumidoDTO);
    }
}
