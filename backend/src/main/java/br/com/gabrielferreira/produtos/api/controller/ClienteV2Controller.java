package br.com.gabrielferreira.produtos.api.controller;

import br.com.gabrielferreira.produtos.api.dto.ClienteDTO;
import br.com.gabrielferreira.produtos.api.dto.create.ClienteCreateDTO;
import br.com.gabrielferreira.produtos.api.dto.update.ClienteSenhaUpdateDTO;
import br.com.gabrielferreira.produtos.api.dto.update.ClienteUpdateDTO;
import br.com.gabrielferreira.produtos.api.mapper.ClienteMapper;
import br.com.gabrielferreira.produtos.domain.model.ClienteV2;
import br.com.gabrielferreira.produtos.domain.service.ClienteV2Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable Long id){
        ClienteV2 cliente = clienteService.buscarClientePorId(id);
        ClienteDTO clienteDTO = clienteMapper.toClienteDto(cliente);

        return ResponseEntity.ok().body(clienteDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteUpdateDTO clienteUpdateDTO){
        ClienteV2 cliente = clienteMapper.toCliente(clienteUpdateDTO);
        ClienteV2 clienteAtualizado = clienteService.atualizarCliente(id, cliente);
        ClienteDTO clienteDTO = clienteMapper.toClienteDto(clienteAtualizado);

        return ResponseEntity.ok().body(clienteDTO);
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<ClienteDTO> atualizarSenhaCliente(@PathVariable Long id, @Valid @RequestBody ClienteSenhaUpdateDTO clienteSenhaUpdateDTO){
        ClienteV2 clienteAtualizado = clienteService.atualizarSenhaCliente(id, clienteSenhaUpdateDTO.getNovaSenha(), clienteSenhaUpdateDTO.getAntigaSenha());
        ClienteDTO clienteDTO = clienteMapper.toClienteDto(clienteAtualizado);

        return ResponseEntity.ok().body(clienteDTO);
    }
}
