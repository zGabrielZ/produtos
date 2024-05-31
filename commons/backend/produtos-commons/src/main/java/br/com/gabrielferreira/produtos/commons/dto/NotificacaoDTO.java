package br.com.gabrielferreira.produtos.commons.dto;

import br.com.gabrielferreira.produtos.commons.enums.EmailTemplateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1026011307982030477L;

    private String nomeRemetente;

    private String titulo;

    private String[] destinatarios;

    private EmailTemplateEnum emailTemplate;

    private transient Map<String, Object> dados;
}
