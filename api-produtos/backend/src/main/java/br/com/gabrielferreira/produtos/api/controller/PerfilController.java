package br.com.gabrielferreira.produtos.api.controller;

import br.com.gabrielferreira.produtos.api.dto.PerfilDTO;
import br.com.gabrielferreira.produtos.api.mapper.PerfilMapper;
import br.com.gabrielferreira.produtos.domain.model.Perfil;
import br.com.gabrielferreira.produtos.domain.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Perfil Controller", description = "Endpoints para realizar requisições de perfis")
@RestController
@RequestMapping("/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    private final PerfilMapper perfilMapper;

    @Operation(summary = "Buscar perfil por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil encontrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PerfilDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> buscarPerfilPorId(@PathVariable Long id){
        Perfil perfil = perfilService.buscarPerfilPorId(id);
        PerfilDTO perfilDTO = perfilMapper.toPerfilDto(perfil);

        return ResponseEntity.ok().body(perfilDTO);
    }

    @Operation(summary = "Buscar perfil por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfis encontrados",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PerfilDTO.class)) })
    })
    @GetMapping
    public ResponseEntity<List<PerfilDTO>> buscarPerfis(){
        List<Perfil> perfis = perfilService.buscarPerfis();
        List<PerfilDTO> perfilDTO = perfilMapper.toPerfisDtos(perfis);

        return ResponseEntity.ok().body(perfilDTO);
    }
}
