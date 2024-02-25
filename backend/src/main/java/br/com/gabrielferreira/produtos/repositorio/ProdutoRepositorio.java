package br.com.gabrielferreira.produtos.repositorio;

import br.com.gabrielferreira.produtos.modelo.entidade.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepositorio extends JpaRepository<Produto,Long>{

}
