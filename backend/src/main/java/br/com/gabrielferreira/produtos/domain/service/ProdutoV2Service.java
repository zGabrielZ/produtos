package br.com.gabrielferreira.produtos.domain.service;

import br.com.gabrielferreira.produtos.domain.model.ProdutoV2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProdutoV2Service {

    ProdutoV2 salvarProduto(ProdutoV2 produtoV2);

    ProdutoV2 buscarProdutoPorId(Long id);

    ProdutoV2 atualizarProduto(Long id, ProdutoV2 produtoV2);

    void deletarProdutoPorId(Long id);

    Page<ProdutoV2> buscarProdutosPaginados(Pageable pageable, String nome, BigDecimal preco);
}
