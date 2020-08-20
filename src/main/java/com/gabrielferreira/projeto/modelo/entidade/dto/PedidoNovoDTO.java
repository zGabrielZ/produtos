package com.gabrielferreira.projeto.modelo.entidade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoNovoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Valid
	@NotNull(message = "Cliente não pode ser nulo")
	private ClienteNovoId cliente;
	
	@Valid
	@NotEmpty(message = "Lista de pedidos não pode ser vazia")
	private List<ItensDTO> itens = new ArrayList<ItensDTO>();
	
	public Double getTotal() {
		double soma = 0.0;
		for(ItensDTO i : itens) {
			soma += i.getSubTotal();
		}
		return soma;
	}

}
