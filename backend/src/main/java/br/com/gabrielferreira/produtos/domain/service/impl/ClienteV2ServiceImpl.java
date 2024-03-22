package br.com.gabrielferreira.produtos.domain.service.impl;

import br.com.gabrielferreira.produtos.domain.repository.ClienteV2Repository;
import br.com.gabrielferreira.produtos.domain.service.ClienteV2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteV2ServiceImpl implements ClienteV2Service {

    private final ClienteV2Repository clienteV2Repository;
}
