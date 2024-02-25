package br.com.gabrielferreira.produtos.repositorio;

import br.com.gabrielferreira.produtos.modelo.entidade.Itens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensRepositorio extends JpaRepository<Itens,Long>{

}
