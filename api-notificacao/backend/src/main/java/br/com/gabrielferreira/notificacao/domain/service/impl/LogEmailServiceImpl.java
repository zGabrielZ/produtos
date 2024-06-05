package br.com.gabrielferreira.notificacao.domain.service.impl;

import br.com.gabrielferreira.notificacao.domain.model.Notificacao;
import br.com.gabrielferreira.notificacao.domain.model.enums.NotificacaoStatusEnum;
import br.com.gabrielferreira.notificacao.domain.service.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class LogEmailServiceImpl implements EmailService {

    @Override
    public Notificacao enviarEmail(Notificacao notificacao) {
        log.debug("enviarEmail notificação : {}", notificacao);
        notificacao.setStatus(NotificacaoStatusEnum.ENVIADO);
        return notificacao;
    }
}
