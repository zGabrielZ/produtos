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
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6168950072508421375L;

    private Long id;

    private ZonedDateTime data;

    private ZonedDateTime dataFinalizado;

    private PedidoStatusEnum pedidoStatus;

    private ZonedDateTime dataInclusao;

    private ZonedDateTime dataAtualizacao;

    private BigDecimal precoTotal;

    private List<ItemPedidoDTO> itensPedidos = new ArrayList<>();
}
