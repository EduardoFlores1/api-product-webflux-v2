package com.ejercicio.validador_nombre.model.dto;

import com.ejercicio.validador_nombre.model.enums.Level;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private String code;
    private String message;
    private Collection<String> details;
    private Level level;
}
