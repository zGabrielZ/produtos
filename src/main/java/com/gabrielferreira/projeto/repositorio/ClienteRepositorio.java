package com.gabrielferreira.projeto.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabrielferreira.projeto.modelo.entidade.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente,Long>{

	public Optional<Cliente> findByEmail(String email);
	
}
