package br.com.gabrielferreira.notificacao.tests;

import br.com.gabrielferreira.notificacao.domain.model.Notificacao;
import br.com.gabrielferreira.produtos.commons.dto.NotificacaoDTO;
import br.com.gabrielferreira.produtos.commons.enums.EmailTemplateEnum;

import java.util.HashMap;
import java.util.Map;

public class NotificacaoFactory {

    private NotificacaoFactory(){}

    public static Notificacao criarNotificacaoEmailModel(){
        Map<String, Object> dados = new HashMap<>();
        dados.put("descricao", "Seu pedido já foi encaminhado");
        dados.put("codigoPedido", 2L);
        dados.put("dataPedido", "29/05/2024 10:00:00");

        return Notificacao.builder()
                .nomeRemetente("Gabriel Ferreira")
                .titulo("Pedido aberto")
                .destinatarios(new String[]{"teste@email.com.br"})
                .emailTemplate(EmailTemplateEnum.PEDIDOS)
                .dados(dados)
                .build();
    }

    public static NotificacaoDTO criarNotificacaoEmailDto(){
        Notificacao notificacao = criarNotificacaoEmailModel();

        return NotificacaoDTO.builder()
                .nomeRemetente(notificacao.getNomeRemetente())
                .titulo(notificacao.getTitulo())
                .destinatarios(notificacao.getDestinatarios())
                .emailTemplate(notificacao.getEmailTemplate())
                .dados(notificacao.getDados())
                .build();
    }
}
