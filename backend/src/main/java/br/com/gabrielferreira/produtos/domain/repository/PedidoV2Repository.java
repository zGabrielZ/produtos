package br.com.gabrielferreira.produtos.domain.repository;

import br.com.gabrielferreira.produtos.domain.model.PedidoV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoV2Repository extends JpaRepository<PedidoV2, Long> {

    @Query("SELECT p FROM PedidoV2 p " +
            "JOIN FETCH p.cliente c " +
            "LEFT JOIN FETCH p.itensPedidos ip " +
            "LEFT JOIN FETCH ip.produto pro " +
            "WHERE c.id = :idCliente AND p.id = :idPedido")
    Optional<PedidoV2> buscarPedido(@Param("idCliente") Long idCliente, @Param("idPedido") Long idPedido);
}
