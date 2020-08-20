package com.gabrielferreira.projeto.exceptions;

public class RegraDeNegocio extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RegraDeNegocio(String mensagem) {
		super(mensagem);
	}

}
