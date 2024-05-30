package br.com.gabrielferreira.notificacao.domain.model;

import br.com.gabrielferreira.notificacao.domain.model.enums.EmailTemplateEnum;
import br.com.gabrielferreira.notificacao.domain.model.enums.NotificacaoStatusEnum;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Notificacao implements Serializable {

    @Serial
    private static final long serialVersionUID = 6748733911205826436L;

    private String nomeRemetente;

    private String titulo;

    private String[] destinatarios;

    private NotificacaoStatusEnum status;

    private EmailTemplateEnum emailTemplate;

    private transient Map<String, Object> dados;
}
