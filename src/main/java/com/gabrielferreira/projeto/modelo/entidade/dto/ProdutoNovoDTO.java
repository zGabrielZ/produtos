package com.gabrielferreira.projeto.modelo.entidade.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoNovoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Nome é obrigatório")
	@Size(max = 120,message = "Tamanho é 120 caracteres")
	private String nome;
	
	@NotNull(message = "Preço não pode ser nulo")
	private Double preco;

}
