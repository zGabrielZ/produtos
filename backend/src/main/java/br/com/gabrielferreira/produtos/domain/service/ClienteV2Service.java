package br.com.gabrielferreira.produtos.domain.service;

import br.com.gabrielferreira.produtos.domain.model.ClienteV2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteV2Service {

    ClienteV2 salvarCliente(ClienteV2 clienteV2);

    ClienteV2 buscarClientePorId(Long id);

    ClienteV2 atualizarCliente(Long id, ClienteV2 clienteV2);

    ClienteV2 atualizarSenhaCliente(Long id, String novaSenha, String antigaSenha);

    void deletarClientePorId(Long id);

    Page<ClienteV2> buscarClientesPaginados(Pageable pageable, String nome, String email);
}
