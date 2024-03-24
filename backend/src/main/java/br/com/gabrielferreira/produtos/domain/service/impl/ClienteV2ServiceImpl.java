package br.com.gabrielferreira.produtos.domain.service.impl;

import br.com.gabrielferreira.produtos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.produtos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.produtos.domain.model.ClienteV2;
import br.com.gabrielferreira.produtos.domain.repository.ClienteV2Repository;
import br.com.gabrielferreira.produtos.domain.service.ClienteV2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.gabrielferreira.produtos.common.utils.ConstantesUtils.*;

@Service
@RequiredArgsConstructor
public class ClienteV2ServiceImpl implements ClienteV2Service {

    private final ClienteV2Repository clienteV2Repository;

    @Transactional
    @Override
    public ClienteV2 salvarCliente(ClienteV2 clienteV2) {
        validarCamposCliente(clienteV2);
        validarSenhaCliente(clienteV2.getSenha());
        validarEmail(clienteV2.getEmail(), null);

        clienteV2 = clienteV2Repository.save(clienteV2);
        return clienteV2;
    }

    @Override
    public ClienteV2 buscarClientePorId(Long id) {
        return clienteV2Repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Cliente não encontrado"));
    }

    @Transactional
    @Override
    public ClienteV2 atualizarCliente(Long id, ClienteV2 clienteV2) {
        ClienteV2 clienteEncontrado = buscarClientePorId(id);

        clienteV2.setNome(clienteV2.getNome().trim());
        clienteEncontrado.setNome(clienteV2.getNome());

        clienteEncontrado = clienteV2Repository.save(clienteEncontrado);
        return clienteEncontrado;
    }

    @Transactional
    @Override
    public ClienteV2 atualizarSenhaCliente(Long id, String novaSenha, String antigaSenha) {
        ClienteV2 clienteEncontrado = buscarClientePorId(id);

        validarSenhaCliente(novaSenha);
        validarSenhaAntiga(antigaSenha, clienteEncontrado.getSenha());
        clienteEncontrado.setSenha(novaSenha);

        clienteEncontrado = clienteV2Repository.save(clienteEncontrado);
        return clienteEncontrado;
    }

    @Transactional
    @Override
    public void deletarClientePorId(Long id) {
        ClienteV2 clienteEncontrado = buscarClientePorId(id);
        clienteV2Repository.delete(clienteEncontrado);
    }

    private void validarSenhaAntiga(String antigaSenha, String senhaCadastrada){
        if(!antigaSenha.equals(senhaCadastrada)){
            throw new RegraDeNegocioException("Senha antiga informada é incompatível");
        }
    }

    private void validarSenhaCliente(String senha){
        if(!isPossuiCaracteresEspecias(senha)){
            throw new RegraDeNegocioException("A senha informada tem que ter pelo menos uma caractere especial");
        }

        if(!isPossuiCaractereMaiusculas(senha)){
            throw new RegraDeNegocioException("A senha informada tem que ter pelo menos uma caractere maiúsculas");
        }

        if(!isPossuiCaractereMinusculas(senha)){
            throw new RegraDeNegocioException("A senha informada tem que ter pelo menos uma caractere minúsculas");
        }

        if(!isPossuiCaractereDigito(senha)){
            throw new RegraDeNegocioException("A senha informada tem que ter pelo menos um caractere dígito");
        }
    }

    private void validarCamposCliente(ClienteV2 clienteV2){
        clienteV2.setNome(clienteV2.getNome().trim());
        clienteV2.setEmail(clienteV2.getEmail().trim());
    }

    private void validarEmail(String email, Long id){
        if(isEmailExistente(email, id)){
            throw new RegraDeNegocioException(String.format("Não vai ser possível cadastrar este cliente pois o e-mail '%s' já foi cadastrado", email));
        }
    }

    private boolean isEmailExistente(String email, Long id){
        if(id == null){
            return clienteV2Repository.buscarPorEmail(email)
                    .isPresent();
        } else {
            return clienteV2Repository.buscarPorEmail(email)
                    .filter(c -> !c.getId().equals(id))
                    .isPresent();
        }
    }
}
