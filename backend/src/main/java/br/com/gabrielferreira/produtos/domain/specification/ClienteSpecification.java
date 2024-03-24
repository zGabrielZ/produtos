package br.com.gabrielferreira.produtos.domain.specification;

import br.com.gabrielferreira.produtos.domain.model.ClienteV2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class ClienteSpecification {

    private ClienteSpecification(){}

    public static Specification<ClienteV2> buscarPorNome(String nome){
        return (root, query, criteriaBuilder) -> {
            if(StringUtils.isNotBlank(nome)){
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<ClienteV2> buscarPorEmail(String email){
        return (root, query, criteriaBuilder) -> {
            if(StringUtils.isNotBlank(email)){
                return criteriaBuilder.equal(root.get("email"), email);
            }
            return null;
        };
    }
}
