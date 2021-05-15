package com.mvam.spperson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    private String nome;

    private String sexo;

    @Email
    private String email;

    @NotNull
    private LocalDate dataNascimento;

    private String naturalidade;
    private String nacionalidade;

    @NotNull
    @Pattern(regexp ="[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}",
            message = "CPF precisa estar num formato valido")
    private String cpf;

    private Instant criadoEm;
    private Instant atualizadoEm;

}
