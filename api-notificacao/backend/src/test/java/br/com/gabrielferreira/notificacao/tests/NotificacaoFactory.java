package br.com.gabrielferreira.notificacao.tests;

import br.com.gabrielferreira.notificacao.api.dto.NotificacaoDTO;
import br.com.gabrielferreira.notificacao.domain.model.Notificacao;
import br.com.gabrielferreira.notificacao.domain.model.enums.EmailTemplateEnum;

import java.util.HashMap;
import java.util.Map;

public class NotificacaoFactory {

    private NotificacaoFactory(){}

    public static NotificacaoDTO criarNotificacaoEmail(){
        Map<String, Object> dados = new HashMap<>();
        dados.put("descricao", "Seu pedido já foi encaminhado");
        dados.put("codigoPedido", 2L);
        dados.put("dataPedido", "29/05/2024 10:00:00");

        return NotificacaoDTO.builder()
                .nomeRemetente("Gabriel Ferreira")
                .titulo("Pedido aberto")
                .destinatarios(new String[]{"teste@email.com.br"})
                .emailTemplate(EmailTemplateEnum.PEDIDOS)
                .dados(dados)
                .build();
    }

    public static Notificacao criarNotificacaoEmailModel(){
        NotificacaoDTO notificacaoDTO = criarNotificacaoEmail();
        return Notificacao.builder()
                .nomeRemetente(notificacaoDTO.getNomeRemetente())
                .titulo(notificacaoDTO.getTitulo())
                .destinatarios(notificacaoDTO.getDestinatarios())
                .emailTemplate(notificacaoDTO.getEmailTemplate())
                .dados(notificacaoDTO.getDados())
                .build();
    }
}
