package com.ejercicio.validador_nombre.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends Exception{

    public final HttpStatus status;

    private ServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static ServiceException newInstance(String message, HttpStatus status) {
        if(!status.isError()) throw new IllegalArgumentException("no valid ".concat(status.name()));

        return new ServiceException(message, status);
    }

    public static ServiceException newInstance4XX(String message) {
        return new ServiceException(message, HttpStatus.BAD_REQUEST);
    }

    public static ServiceException newInstance5XX(String message) {
        return new ServiceException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ServiceException notFound(T id) {
        return new ServiceException("No se encontro el id: " + id, HttpStatus.NOT_FOUND);
    }
}
