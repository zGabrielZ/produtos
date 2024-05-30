package br.com.gabrielferreira.notificacao.domain.service.impl;

import br.com.gabrielferreira.notificacao.domain.model.Notificacao;
import br.com.gabrielferreira.notificacao.domain.model.enums.NotificacaoStatusEnum;
import br.com.gabrielferreira.notificacao.domain.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogEmailServiceImpl implements EmailService {

    @Override
    public Notificacao enviarEmail(Notificacao notificacao) {
        log.info("Notificação a enviar {}", notificacao);
        notificacao.setStatus(NotificacaoStatusEnum.ENVIADO);
        return notificacao;
    }
}
