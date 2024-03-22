package br.com.gabrielferreira.produtos.api.controller;

import br.com.gabrielferreira.produtos.api.dto.ProdutoDTO;
import br.com.gabrielferreira.produtos.api.dto.create.ProdutoCreateDTO;
import br.com.gabrielferreira.produtos.api.dto.update.ProdutoUpdateDTO;
import br.com.gabrielferreira.produtos.api.mapper.ProdutoMapper;
import br.com.gabrielferreira.produtos.domain.model.ProdutoV2;
import br.com.gabrielferreira.produtos.domain.service.ProdutoV2Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/v2/produtos")
@RequiredArgsConstructor
public class ProdutoV2Controller {

    private final ProdutoV2Service produtoV2Service;

    private final ProdutoMapper produtoMapper;

    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrarProduto(@Valid @RequestBody ProdutoCreateDTO produtoCreateDTO){
        ProdutoV2 produto = produtoMapper.toProduto(produtoCreateDTO);
        ProdutoV2 produtoCadastrado = produtoV2Service.salvarProduto(produto);
        ProdutoDTO produtoDTO = produtoMapper.toProdutoDto(produtoCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(produtoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(produtoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProdutoPorId(@PathVariable Long id){
        ProdutoV2 produto = produtoV2Service.buscarProdutoPorId(id);
        ProdutoDTO produtoDTO = produtoMapper.toProdutoDto(produto);

        return ResponseEntity.ok().body(produtoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProdutoPorId(@PathVariable Long id, @Valid @RequestBody ProdutoUpdateDTO produtoUpdateDTO){
        ProdutoV2 produto = produtoMapper.toProduto(produtoUpdateDTO);
        ProdutoV2 produtoAtualizado = produtoV2Service.atualizarProduto(id, produto);
        ProdutoDTO produtoDTO = produtoMapper.toProdutoDto(produtoAtualizado);

        return ResponseEntity.ok().body(produtoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProdutoPorId(@PathVariable Long id){
        produtoV2Service.deletarProdutoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> buscarProdutosPaginados(@PageableDefault(size = 5, sort = "dataInclusao", direction = Sort.Direction.DESC) Pageable pageable,
                                                                    @RequestParam(required = false) String nome,
                                                                    @RequestParam(required = false) BigDecimal preco){
        Page<ProdutoV2> produtos = produtoV2Service.buscarProdutosPaginados(pageable, nome, preco);
        Page<ProdutoDTO> produtosDtos = produtoMapper.toProdutosDtos(produtos);

        return ResponseEntity.ok().body(produtosDtos);
    }
}
