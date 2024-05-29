package br.com.gabrielferreira.produtos.tests;

import br.com.gabrielferreira.produtos.api.dto.create.ProdutoCreateDTO;
import br.com.gabrielferreira.produtos.api.dto.update.ProdutoUpdateDTO;

import java.math.BigDecimal;

public class ProdutoFactory {

    private ProdutoFactory(){}

    public static ProdutoCreateDTO criarProduto(){
        return ProdutoCreateDTO.builder()
                .nome("Laranja")
                .preco(BigDecimal.valueOf(4.00))
                .build();
    }

    public static ProdutoUpdateDTO atualizarProduto(){
        ProdutoUpdateDTO produtoUpdateDTO = new ProdutoUpdateDTO();
        produtoUpdateDTO.setNome("Laranja Limão");
        produtoUpdateDTO.setPreco(BigDecimal.valueOf(10.00));
        return produtoUpdateDTO;
    }
}
