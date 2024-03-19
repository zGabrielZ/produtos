package br.com.gabrielferreira.produtos.domain.specification;

import br.com.gabrielferreira.produtos.domain.model.ProdutoV2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProdutoSpecification {

    private ProdutoSpecification(){}

    public static Specification<ProdutoV2> buscarPorNome(String nome){
        return (root, query, criteriaBuilder) -> {
            if(StringUtils.isNotBlank(nome)){
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<ProdutoV2> buscarPorPreco(BigDecimal preco){
        return (root, query, criteriaBuilder) -> {
            if(preco != null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), preco);
            }
            return null;
        };
    }
}
