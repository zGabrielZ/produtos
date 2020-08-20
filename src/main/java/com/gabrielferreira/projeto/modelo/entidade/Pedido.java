package com.gabrielferreira.projeto.modelo.entidade;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gabrielferreira.projeto.modelo.entidade.enums.PedidoStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDateTime dataDoPedido;
	
	private LocalDateTime dataDoPedidoFinalizada;
	
	@Enumerated(EnumType.STRING)
	private PedidoStatus pedidoStatus;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@OneToMany(mappedBy = "pedido")
	private List<Itens> itens = new ArrayList<Itens>();

	public Pedido(Long id, LocalDateTime dataDoPedido, PedidoStatus pedidoStatus, Cliente cliente) {
		this.id = id;
		this.dataDoPedido = dataDoPedido;
		this.pedidoStatus = pedidoStatus;
		this.cliente = cliente;
	}
	
	public Double getTotal() {
		double soma = 0.0;
		for(Itens i : itens) {
			soma += i.getSubTotal();
		}
		return soma;
	}
	
	public void finalizarPedido() {
		setPedidoStatus(PedidoStatus.FINALIZADO);
		setDataDoPedidoFinalizada(LocalDateTime.now());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	

}
