package br.com.gabrielferreira.produtos.api.controller;

import br.com.gabrielferreira.produtos.api.dto.UsuarioDTO;
import br.com.gabrielferreira.produtos.api.dto.UsuarioResumidoDTO;
import br.com.gabrielferreira.produtos.api.dto.create.UsuarioCreateDTO;
import br.com.gabrielferreira.produtos.api.dto.update.UsuarioSenhaUpdateDTO;
import br.com.gabrielferreira.produtos.api.dto.update.UsuarioUpdateDTO;
import br.com.gabrielferreira.produtos.api.mapper.UsuarioMapper;
import br.com.gabrielferreira.produtos.domain.model.Usuario;
import br.com.gabrielferreira.produtos.domain.service.UsuarioService;
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

@Tag(name = "Usuário Controller", description = "Endpoints para realizar requisições de usuário")
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final UsuarioMapper usuarioMapper;

    @Operation(summary = "Cadastrar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Regra de negócio",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO){
        Usuario usuario = usuarioMapper.toUsuario(usuarioCreateDTO);
        Usuario usuarioCadastrado = usuarioService.salvarUsuario(usuario);
        UsuarioDTO usuarioDTO = usuarioMapper.toUsuarioDto(usuarioCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(usuarioDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }

    @Operation(summary = "Buscar usuário por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResumidoDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResumidoDTO> buscarUsuarioPorId(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        UsuarioResumidoDTO usuarioResumidoDTO = usuarioMapper.toUsuarioResumidoDto(usuario);

        return ResponseEntity.ok().body(usuarioResumidoDTO);
    }

    @Operation(summary = "Atualizar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Regra de negócio",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO){
        Usuario usuario = usuarioMapper.toUsuario(usuarioUpdateDTO);
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
        UsuarioDTO usuarioDTO = usuarioMapper.toUsuarioDto(usuarioAtualizado);

        return ResponseEntity.ok().body(usuarioDTO);
    }

    @Operation(summary = "Atualizar senha do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Regra de negócio",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content)
    })
    @PutMapping("/{id}/senha")
    public ResponseEntity<UsuarioDTO> atualizarSenhaUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaUpdateDTO usuarioSenhaUpdateDTO){
        Usuario usuarioAtualizado = usuarioService.atualizarSenhaUsuario(id, usuarioSenhaUpdateDTO.getNovaSenha(), usuarioSenhaUpdateDTO.getAntigaSenha());
        UsuarioDTO usuarioDTO = usuarioMapper.toUsuarioDto(usuarioAtualizado);

        return ResponseEntity.ok().body(usuarioDTO);
    }

    @Operation(summary = "Deletar usuário por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioPorId(@PathVariable Long id){
        usuarioService.deletarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResumidoDTO.class)) })
    })
    @GetMapping
    public ResponseEntity<Page<UsuarioResumidoDTO>> buscarUsuariosPaginados(@PageableDefault(size = 5, sort = "dataInclusao", direction = Sort.Direction.DESC) Pageable pageable,
                                                                            @RequestParam(required = false) String nome,
                                                                            @RequestParam(required = false) String email){
        Page<Usuario> usuarios = usuarioService.buscarUsuariosPaginados(pageable, nome, email);
        Page<UsuarioResumidoDTO> usuarioResumidoDTOS = usuarioMapper.toUsuarioResumidoDtos(usuarios);

        return ResponseEntity.ok().body(usuarioResumidoDTOS);
    }
}
