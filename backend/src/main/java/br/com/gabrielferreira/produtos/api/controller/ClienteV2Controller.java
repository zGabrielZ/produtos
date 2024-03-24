package br.com.gabrielferreira.produtos.api.controller;

import br.com.gabrielferreira.produtos.api.dto.ClienteDTO;
import br.com.gabrielferreira.produtos.api.dto.create.ClienteCreateDTO;
import br.com.gabrielferreira.produtos.api.mapper.ClienteMapper;
import br.com.gabrielferreira.produtos.domain.model.ClienteV2;
import br.com.gabrielferreira.produtos.domain.service.ClienteV2Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v2/clientes")
@RequiredArgsConstructor
public class ClienteV2Controller {

    private final ClienteV2Service clienteService;

    private final ClienteMapper clienteMapper;

    @PostMapping
    public ResponseEntity<ClienteDTO> cadastrarCliente(@Valid @RequestBody ClienteCreateDTO clienteCreateDTO){
        ClienteV2 cliente = clienteMapper.toCliente(clienteCreateDTO);
        ClienteV2 clienteCadastrado = clienteService.salvarCliente(cliente);
        ClienteDTO clienteDTO = clienteMapper.toClienteDto(clienteCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(clienteDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(clienteDTO);
    }
}
