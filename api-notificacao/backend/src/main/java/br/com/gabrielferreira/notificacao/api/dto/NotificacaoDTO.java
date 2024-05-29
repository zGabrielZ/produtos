package br.com.gabrielferreira.notificacao.api.dto;

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1026011307982030477L;

    @NotBlank
    @Size(min = 1, max = 150)
    private String titulo;

    @Size(min = 1, max = 255)
    private String descricao;

    @NotBlank
    @Size(min = 1, max = 150)
    private String de;

    @NotNull
    @NotEmpty
    private String[] destinatarios;
}
