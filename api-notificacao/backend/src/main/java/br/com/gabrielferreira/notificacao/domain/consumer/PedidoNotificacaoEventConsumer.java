package br.com.gabrielferreira.notificacao.domain.consumer;

import br.com.gabrielferreira.notificacao.domain.model.Notificacao;
import br.com.gabrielferreira.notificacao.domain.model.enums.NotificacaoStatusEnum;
import br.com.gabrielferreira.notificacao.domain.service.NotificacaoService;
import br.com.gabrielferreira.produtos.commons.dto.NotificacaoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class PedidoNotificacaoEventConsumer {

    private final NotificacaoService notificacaoService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${broker.queue.pedido.notificacao.event}", durable = "true"),
            exchange = @Exchange(value = "${broker.exchange.pedido.notificacao.event}", type = ExchangeTypes.TOPIC), ignoreDeclarationExceptions = "true",
            key = "${broker.key.pedido.notificacao.event}"
    ))
    public void listenPedidoNotifcacaoEvent(@Payload NotificacaoDTO notificacaoDTO){
        log.debug("listenPedidoNotifcacaoEvent notificação recebida : {}", notificacaoDTO);

        Notificacao notificacao = notificacaoService.enviarNotificacao(converterParaNotificacao(notificacaoDTO));

        if(NotificacaoStatusEnum.isEnviado(notificacao.getStatus())){
            log.info("Mensagem enviada com sucesso");
        } else {
            log.info("Mensagem não foi enviada");
        }
    }

    private Notificacao converterParaNotificacao(NotificacaoDTO notificacaoDTO){
        return Notificacao.builder()
                .nomeRemetente(notificacaoDTO.getNomeRemetente())
                .titulo(notificacaoDTO.getTitulo())
                .destinatarios(notificacaoDTO.getDestinatarios())
                .emailTemplate(notificacaoDTO.getEmailTemplate())
                .dados(notificacaoDTO.getDados())
                .build();
    }
}
