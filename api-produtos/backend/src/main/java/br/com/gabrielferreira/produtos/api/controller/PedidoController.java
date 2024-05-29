package br.com.gabrielferreira.produtos.api.controller;

import br.com.gabrielferreira.produtos.api.dto.PedidoDTO;
import br.com.gabrielferreira.produtos.api.dto.PedidoResumidoDTO;
import br.com.gabrielferreira.produtos.api.dto.create.PedidoCreateDTO;
import br.com.gabrielferreira.produtos.api.mapper.PedidoMapper;
import br.com.gabrielferreira.produtos.domain.model.Pedido;
import br.com.gabrielferreira.produtos.domain.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Pedido Controller", description = "Endpoints para realizar requisições de pedidos")
@RestController
@RequestMapping("/usuarios/{idUsuario}/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    private final PedidoMapper pedidoMapper;

    @Operation(summary = "Cadastrar pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido cadastrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PedidoDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Recurso não encontrado",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<PedidoDTO> cadastrarPedido(@PathVariable Long idUsuario, @Valid @RequestBody PedidoCreateDTO pedidoCreateDTO){
        Pedido pedido = pedidoMapper.toPedido(pedidoCreateDTO);
        Pedido pedidoCadastrado = pedidoService.salvarPedido(idUsuario, pedido);
        PedidoDTO pedidoDTO = pedidoMapper.toPedidoDto(pedidoCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(pedidoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(pedidoDTO);
    }

    @Operation(summary = "Buscar pedido por id do usuário e id do pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PedidoResumidoDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResumidoDTO> buscarPedidoPorId(@PathVariable Long idUsuario, @PathVariable Long id){
        Pedido pedido = pedidoService.buscarPedidoPorId(idUsuario, id);
        PedidoResumidoDTO pedidoResumidoDTO = pedidoMapper.toPedidoResumidoDto(pedido);

        return ResponseEntity.ok().body(pedidoResumidoDTO);
    }

    @Operation(summary = "Finalizar pedido por id usuario e id do pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido finalizado",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Regra de negócio",
                    content = @Content)
    })
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizarPedidoPorId(@PathVariable Long idUsuario, @PathVariable Long id){
        pedidoService.finalizarPedidoPorId(idUsuario, id);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cancelar pedido por id usuario e id do pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido cancelado",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Regra de negócio",
                    content = @Content)
    })
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPedidoPorId(@PathVariable Long idUsuario, @PathVariable Long id){
        pedidoService.cancelarPedidoPorId(idUsuario, id);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar pedidos por id usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PedidoResumidoDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Usuário não encontrado",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<PedidoResumidoDTO>> buscarPedidosPaginados(@PathVariable Long idUsuario,
                                                                          @PageableDefault(size = 5, sort = "dataInclusao", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Pedido> pedidos = pedidoService.buscarPedidosPaginados(idUsuario, pageable);
        Page<PedidoResumidoDTO> pedidoResumidoDTOS = pedidoMapper.toPedidoResumidoDtos(pedidos);

        return ResponseEntity.ok().body(pedidoResumidoDTOS);
    }
}
