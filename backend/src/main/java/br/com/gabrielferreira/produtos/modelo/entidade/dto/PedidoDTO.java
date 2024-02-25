package br.com.gabrielferreira.produtos.modelo.entidade.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import br.com.gabrielferreira.produtos.modelo.entidade.enums.PedidoStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataDoPedido;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataDoPedidoFinalizada;
	
	@Enumerated(EnumType.STRING)
	private PedidoStatus pedidoStatus;
	
	private ClienteDTO cliente;
	
	private List<ItensDTO> itens = new ArrayList<ItensDTO>();
	
	public Double getTotal() {
		double soma = 0.0;
		for(ItensDTO i : itens) {
			soma += i.getSubTotal();
		}
		return soma;
	}

}
