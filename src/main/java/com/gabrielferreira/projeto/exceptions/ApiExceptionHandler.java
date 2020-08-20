package com.gabrielferreira.projeto.exceptions;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gabrielferreira.projeto.exceptions.Erro.Campos;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers,HttpStatus httpStatus,WebRequest request){
		Erro erro = new Erro();
		
		List<Campos> campos = new ArrayList<Erro.Campos>();
		
		for(ObjectError error:ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String msg = error.getDefaultMessage();
			campos.add(new Erro.Campos(nome,msg));
		}
		
		erro.setStatus(httpStatus.value());
		erro.setDataHorario(LocalDateTime.now());
		erro.setMensagem("Um ou mais campos estão inválidos,Faça o preenchimento correto e tenta novamente");
		erro.setCampos(campos);
		
		return super.handleExceptionInternal(ex, erro, headers, httpStatus, request);
		
	}
	
	@ExceptionHandler(ObjetoNaoEncotrado.class)
	public ResponseEntity<Erro> objNaoEncontrado(ObjetoNaoEncotrado e,
			HttpServletRequest req){	
		Erro erro = new Erro(HttpStatus.NOT_FOUND.value(),e.getMessage(),LocalDateTime.now(),null);
		return ResponseEntity.status(erro.getStatus()).body(erro);
	}
	
	@ExceptionHandler(RegraDeNegocio.class)
	public ResponseEntity<Erro> regraDeNegocio(RegraDeNegocio e,
			HttpServletRequest req){
		Erro erro = new Erro(HttpStatus.BAD_REQUEST.value(),e.getMessage(),LocalDateTime.now(),null);
		return ResponseEntity.status(erro.getStatus()).body(erro);
	}
		

}
