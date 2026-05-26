package com.innovatech.msvc_proyecto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProyectoNoEncontradoException extends RuntimeException {

    public ProyectoNoEncontradoException(Long id) {
        super("Proyecto con id " + id + " no fue encontrado");
    }
}