package br.com.gabrielferreira.produtos.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -178664660432861224L;

    private Long id;

    private String nome;

    private BigDecimal preco;

    private ZonedDateTime dataInclusao;

    private ZonedDateTime dataAtualizacao;
}
