package com.mvam.spperson.services.exceptions;

public class PessoaComMesmoCPFException extends RuntimeException {
    public PessoaComMesmoCPFException(String cpf) {
        super(String.format("Ja existe uma pessoa cadastrada com o CPF %s", cpf));
    }
}
