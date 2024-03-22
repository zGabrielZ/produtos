package br.com.gabrielferreira.produtos.api.dto.create;

import jakarta.validation.constraints.*;
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
public class ProdutoCreateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7083617163385312973L;

    @NotBlank
    @Size(max = 150)
    private String nome;

    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2)
    private BigDecimal preco;
}
