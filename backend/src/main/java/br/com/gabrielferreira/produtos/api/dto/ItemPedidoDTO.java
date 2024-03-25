package br.com.gabrielferreira.produtos.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemPedidoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3669665560560096730L;

    private Long id;

    private Integer quantidade;

    private BigDecimal subTotal;

    private ProdutoDTO produto;
}
