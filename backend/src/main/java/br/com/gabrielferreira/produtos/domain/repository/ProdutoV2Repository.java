package br.com.gabrielferreira.produtos.domain.repository;

import br.com.gabrielferreira.produtos.domain.model.ProdutoV2;
import br.com.gabrielferreira.produtos.domain.repository.projection.ProdutoV2MinProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoV2Repository extends JpaRepository<ProdutoV2, Long> {

    @Query("SELECT p.id as id, p.nome as nome FROM ProdutoV2 p " +
            "WHERE p.nome = :nome")
    Optional<ProdutoV2MinProjection> buscarPorNome(@Param("nome") String nome);

    Page<ProdutoV2> findAll(Specification<ProdutoV2> specification, Pageable pageable);
}
