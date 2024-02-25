package br.com.gabrielferreira.produtos.repositorio;

import br.com.gabrielferreira.produtos.modelo.entidade.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido,Long>{
}
