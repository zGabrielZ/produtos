package br.com.gabrielferreira.produtos.domain.repository;

import br.com.gabrielferreira.produtos.domain.model.ClienteV2;
import br.com.gabrielferreira.produtos.domain.repository.projection.ClienteV2MinProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteV2Repository extends JpaRepository<ClienteV2, Long> {

    @Query("SELECT c.id as id, c.email as email FROM ClienteV2 c " +
            "WHERE c.email = :email")
    Optional<ClienteV2MinProjection> buscarPorEmail(@Param("email") String email);
}
