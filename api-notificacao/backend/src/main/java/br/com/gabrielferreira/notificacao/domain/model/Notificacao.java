package br.com.gabrielferreira.notificacao.domain.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Notificacao implements Serializable {

    @Serial
    private static final long serialVersionUID = 6748733911205826436L;

    private String titulo;

    private String descricao;

    private String de;

    private String[] destinatarios;
}
