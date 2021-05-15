package com.mvam.spperson.services.exceptions;

public class RecursoNaoEncontradoException extends RuntimeException{

    public RecursoNaoEncontradoException(Long id) {
        super(String.format("Recurso com ID %s nao encontrado", id));
    }
}
