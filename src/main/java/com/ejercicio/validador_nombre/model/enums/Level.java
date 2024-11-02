package com.ejercicio.validador_nombre.model.enums;

import org.springframework.http.HttpStatus;

public enum Level {
    FATAL, WARNING, UNKNOWN;

    public static Level of(HttpStatus httpStatus) {
        if(httpStatus.is4xxClientError() || httpStatus.is3xxRedirection())
            return WARNING;
        if(httpStatus.is5xxServerError())
            return FATAL;

        return UNKNOWN;
    }
}
