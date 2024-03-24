package br.com.gabrielferreira.produtos.api.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class ClienteUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7534896478690802251L;

    @NotBlank
    @Size(min = 1, max = 255)
    private String nome;
}
