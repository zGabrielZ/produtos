package com.gabrielferreira.projeto.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Erro implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private String mensagem;
	private LocalDateTime dataHorario;
	
	private List<Campos> campos = new ArrayList<Erro.Campos>();
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Campos{
		private String nome;
		private String mensagem;
	}

}
