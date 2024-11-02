package com.ejercicio.validador_nombre.exception;

import org.springframework.http.HttpStatus;

public class ArgumentInvalidException extends RuntimeException{
    private HttpStatus status;
    public ArgumentInvalidException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
