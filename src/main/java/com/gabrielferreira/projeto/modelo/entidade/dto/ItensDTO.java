package com.gabrielferreira.projeto.modelo.entidade.dto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItensDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Valid
	@NotNull(message = "Quantidade não pode ser nulo")
	private Integer quantidade;
	
	@Valid
	@NotNull(message = "Produto não pode ser nulo")
	private ProdutoDTO produto;
	
	public Double getSubTotal() {
		return quantidade * produto.getPreco();
	}

}
