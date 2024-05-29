package br.com.gabrielferreira.produtos.domain.exception;

import java.io.Serial;

public class NaoEncontradoException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3163359517631172271L;

    public NaoEncontradoException(String msg){
        super(msg);
    }
}
