package br.com.gabrielferreira.produtos.domain.publisher;

import br.com.gabrielferreira.produtos.commons.dto.NotificacaoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PedidoNotificacaoEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    private final String exchange;

    private final String routingKey;

    public PedidoNotificacaoEventPublisher(RabbitTemplate rabbitTemplate, @Value("${broker.exchange.pedido.notificacao.event}") String exchange,
                                           @Value("${broker.key.pedido.notificacao.event}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void publishPedidoNotificacaoEvent(NotificacaoDTO notificacaoDTO){
        log.info("Publicando mensagem {}, exchange {}, rounting key {}", notificacaoDTO, exchange, routingKey);

        rabbitTemplate.convertAndSend(exchange, routingKey, notificacaoDTO);

        log.info("Mensagem enviado com sucesso");
    }
}
