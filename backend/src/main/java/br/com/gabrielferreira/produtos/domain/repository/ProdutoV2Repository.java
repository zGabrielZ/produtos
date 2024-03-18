package br.com.gabrielferreira.produtos.domain.repository;

import br.com.gabrielferreira.produtos.domain.model.ProdutoV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoV2Repository extends JpaRepository<ProdutoV2, Long> {
}
