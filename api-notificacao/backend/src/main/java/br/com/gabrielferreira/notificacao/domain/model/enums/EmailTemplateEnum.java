package br.com.gabrielferreira.notificacao.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailTemplateEnum {

    PEDIDOS("pedidos");

    private final String templateEmail;
}
