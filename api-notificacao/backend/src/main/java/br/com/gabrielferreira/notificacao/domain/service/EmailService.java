package br.com.gabrielferreira.notificacao.domain.service;

import br.com.gabrielferreira.notificacao.domain.model.Notificacao;

public interface EmailService {

    Notificacao enviarEmail(Notificacao notificacao);
}
