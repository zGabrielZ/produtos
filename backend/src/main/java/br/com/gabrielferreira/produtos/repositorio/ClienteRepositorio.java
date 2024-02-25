package br.com.gabrielferreira.produtos.repositorio;

import java.util.Optional;

import br.com.gabrielferreira.produtos.modelo.entidade.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente,Long>{

	public Optional<Cliente> findByEmail(String email);
	
}
