package br.com.gabrielferreira.notificacao.domain.service;

import br.com.gabrielferreira.notificacao.domain.model.Notificacao;

public interface NotificacaoService {

    Notificacao enviarNotificacao(Notificacao notificacao);
}
