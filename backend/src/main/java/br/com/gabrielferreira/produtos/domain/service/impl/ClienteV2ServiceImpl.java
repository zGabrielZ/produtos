package br.com.gabrielferreira.produtos.domain.service.impl;

import br.com.gabrielferreira.produtos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.produtos.domain.model.ClienteV2;
import br.com.gabrielferreira.produtos.domain.repository.ClienteV2Repository;
import br.com.gabrielferreira.produtos.domain.service.ClienteV2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteV2ServiceImpl implements ClienteV2Service {

    private final ClienteV2Repository clienteV2Repository;

    @Transactional
    @Override
    public ClienteV2 salvarCliente(ClienteV2 clienteV2) {
        validarEmail(clienteV2.getEmail(), null);
        clienteV2 = clienteV2Repository.save(clienteV2);
        return clienteV2;
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
