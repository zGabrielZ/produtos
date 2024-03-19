package br.com.gabrielferreira.produtos.domain.service.impl;

import br.com.gabrielferreira.produtos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.produtos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.produtos.domain.model.ProdutoV2;
import br.com.gabrielferreira.produtos.domain.repository.ProdutoV2Repository;
import br.com.gabrielferreira.produtos.domain.service.ProdutoV2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static br.com.gabrielferreira.produtos.domain.specification.ProdutoSpecification.*;

@Service
@RequiredArgsConstructor
public class ProdutoV2ServiceImpl implements ProdutoV2Service {

    private final ProdutoV2Repository produtoV2Repository;

    @Transactional
    @Override
    public ProdutoV2 salvarProduto(ProdutoV2 produtoV2) {
        validarCamposProduto(produtoV2);
        validarNomeProduto(produtoV2.getNome(), null);

        produtoV2 = produtoV2Repository.save(produtoV2);
        return produtoV2;
    }

    @Override
    public ProdutoV2 buscarProdutoPorId(Long id) {
        return produtoV2Repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Produto não encontrado"));
    }

    @Transactional
    @Override
    public ProdutoV2 atualizarProduto(Long id, ProdutoV2 produtoV2) {
        ProdutoV2 produtoEncontrado = buscarProdutoPorId(id);

        validarCamposProduto(produtoV2);
        validarNomeProduto(produtoV2.getNome(), id);

        preencherCamposProduto(produtoEncontrado, produtoV2);

        produtoV2 = produtoV2Repository.save(produtoEncontrado);
        return produtoV2;
    }

    @Transactional
    @Override
    public void deletarProdutoPorId(Long id) {
        ProdutoV2 produtoEncontrado = buscarProdutoPorId(id);
        produtoV2Repository.delete(produtoEncontrado);
    }

    @Override
    public Page<ProdutoV2> buscarProdutosPaginados(Pageable pageable, String nome, BigDecimal preco) {
        Specification<ProdutoV2> specification = Specification.where(
                buscarPorNome(nome)
                        .and(buscarPorPreco(preco))
        );

        return produtoV2Repository.findAll(specification, pageable);
    }

    private void validarCamposProduto(ProdutoV2 produtoV2){
        produtoV2.setNome(produtoV2.getNome().trim());
    }

    private void validarNomeProduto(String nome, Long id){
        if(isNomeExistente(nome, id)){
            throw new RegraDeNegocioException(String.format("Não vai ser possível cadastrar este produto pois o nome '%s' já foi cadastrado", nome));
        }
    }

    private boolean isNomeExistente(String nome, Long id){
        if(id == null){
            return produtoV2Repository.buscarPorNome(nome)
                    .isPresent();
        } else {
            return produtoV2Repository.buscarPorNome(nome)
                    .filter(p -> !p.getId().equals(id))
                    .isPresent();
        }
    }

    private void preencherCamposProduto(ProdutoV2 produtoEncontrado, ProdutoV2 produto){
        produtoEncontrado.setNome(produto.getNome());
        produtoEncontrado.setPreco(produto.getPreco());
    }
}
