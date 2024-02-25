package br.com.gabrielferreira.produtos.service.impl;
import java.util.List;
import java.util.Optional;

import br.com.gabrielferreira.produtos.modelo.entidade.Cliente;
import br.com.gabrielferreira.produtos.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabrielferreira.produtos.exceptions.ObjetoNaoEncotrado;
import br.com.gabrielferreira.produtos.exceptions.RegraDeNegocio;
import br.com.gabrielferreira.produtos.repositorio.ClienteRepositorio;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepositorio clienteRepositorio;

	@Override
	public Cliente inserir(Cliente cliente) {
		validarEmail(cliente.getEmail());
		return clienteRepositorio.save(cliente);
	}

	@Override
	public Cliente procurarId(Long id) {
		Optional<Cliente> cliente = clienteRepositorio.findById(id);
		return cliente.orElseThrow(() -> new ObjetoNaoEncotrado("Cliente não encontrado"));
	}

	@Override
	public void validarEmail(String email) {
		Optional<Cliente> cliente = clienteRepositorio.findByEmail(email);
		if(cliente.isPresent()) {
			throw new RegraDeNegocio("Email já existente");
		}
	}

	@Override
	public List<Cliente> listagem() {
		return clienteRepositorio.findAll();
	}	

}
