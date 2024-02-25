package br.com.gabrielferreira.produtos.modelo.entidade.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteNovoId implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Cliente id não pode ser nulo")
	private Long id;

}
