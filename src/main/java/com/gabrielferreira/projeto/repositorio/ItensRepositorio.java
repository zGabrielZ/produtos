package com.gabrielferreira.projeto.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gabrielferreira.projeto.modelo.entidade.Itens;

@Repository
public interface ItensRepositorio extends JpaRepository<Itens,Long>{

}
