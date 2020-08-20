package com.gabrielferreira.projeto.modelo.entidade.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteNovoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Nome é obrigatório")
	@Size(max = 120,message = "Tamanho é 120 caracteres")
	private String nome;
	
	@NotBlank(message = "Email é obrigatório")
	@Size(max = 120,message = "Tamanho é 120 caracteres")
	private String email;
	
	@NotBlank(message = "Senha é obrigatório")
	@Size(max = 120,message = "Tamanho é 120 caracteres")
	private String senha;
}
