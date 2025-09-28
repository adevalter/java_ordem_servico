package br.com.adeweb.ordemservico.core.exception;

public class ClienteException extends RuntimeException{
    public ClienteException(String message, Throwable cause) {
        super(message, cause);
    }
}
