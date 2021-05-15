package com.mvam.spperson.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ErroDTO implements Serializable {
    private Instant dataHora;
    private Integer status;
    private String erro;
    private String mensagem;
    private String endereco;
    private String metodo;

    private List<Campos> campos;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Campos {
        private String nome;
        private String mensagem;
    }
}
