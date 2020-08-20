package com.gabrielferreira.projeto.exceptions;

public class ObjetoNaoEncotrado extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ObjetoNaoEncotrado(String mensagem) {
		super(mensagem);
	}

}
