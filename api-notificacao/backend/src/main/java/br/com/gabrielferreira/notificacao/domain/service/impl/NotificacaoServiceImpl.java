package br.com.gabrielferreira.notificacao.domain.service.impl;

import br.com.gabrielferreira.notificacao.domain.model.Notificacao;
import br.com.gabrielferreira.notificacao.domain.service.EmailService;
import br.com.gabrielferreira.notificacao.domain.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacaoServiceImpl implements NotificacaoService {

    private final EmailService emailService;

    @Override
    public Notificacao enviarNotificacao(Notificacao notificacao) {
        notificacao = emailService.enviarEmail(notificacao);
        return notificacao;
    }
}
