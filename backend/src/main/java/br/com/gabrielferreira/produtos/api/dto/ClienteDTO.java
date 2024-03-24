package br.com.gabrielferreira.produtos.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6281932489585318505L;

    private Long id;

    private String nome;

    private String email;

    private ZonedDateTime dataInclusao;

    private ZonedDateTime dataAtualizacao;
}
