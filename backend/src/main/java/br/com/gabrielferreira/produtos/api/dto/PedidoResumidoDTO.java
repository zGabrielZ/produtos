package br.com.gabrielferreira.produtos.api.dto;

import br.com.gabrielferreira.produtos.domain.model.enums.PedidoStatusEnum;
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
public class PedidoResumidoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7139919889912820004L;

    private Long id;

    private ZonedDateTime data;

    private ZonedDateTime dataFinalizado;

    private PedidoStatusEnum pedidoStatus;

    private ClienteResumidoDTO cliente;

    private BigDecimal precoTotal;

    private ZonedDateTime dataInclusao;

    private ZonedDateTime dataAtualizacao;
}
