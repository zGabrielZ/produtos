package br.com.gabrielferreira.notificacao.api.dto;

import br.com.gabrielferreira.notificacao.domain.model.enums.EmailTemplateEnum;
import br.com.gabrielferreira.notificacao.domain.model.enums.NotificacaoStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(min = 1, max = 150)
    private String nomeRemetente;

    @NotBlank
    @Size(min = 1, max = 150)
    private String titulo;

    @NotNull
    @NotEmpty
    private String[] destinatarios;

    @NotNull
    private EmailTemplateEnum emailTemplate;

    @NotNull
    @NotEmpty
    private transient Map<String, Object> dados;

    private NotificacaoStatusEnum status;
}
