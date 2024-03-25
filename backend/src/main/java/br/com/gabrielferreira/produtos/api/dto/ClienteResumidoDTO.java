package br.com.gabrielferreira.produtos.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResumidoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2867513301215529004L;

    private Long id;

    private String nome;
}
