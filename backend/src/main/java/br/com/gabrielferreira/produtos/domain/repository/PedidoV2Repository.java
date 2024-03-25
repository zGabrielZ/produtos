package br.com.gabrielferreira.produtos.domain.repository;

import br.com.gabrielferreira.produtos.domain.model.PedidoV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoV2Repository extends JpaRepository<PedidoV2, Long> {
}
