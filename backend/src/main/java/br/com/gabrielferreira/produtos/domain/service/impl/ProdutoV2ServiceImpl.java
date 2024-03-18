package br.com.gabrielferreira.produtos.domain.service.impl;

import br.com.gabrielferreira.produtos.domain.repository.ProdutoV2Repository;
import br.com.gabrielferreira.produtos.domain.service.ProdutoV2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoV2ServiceImpl implements ProdutoV2Service {

    private final ProdutoV2Repository produtoV2Repository;
}
