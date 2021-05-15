package com.mvam.spperson.entities;

import lombok.*;

import javax.persistence.*;
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
@Entity
@Table(name = "tb_pessoa")
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Email
    private String email;

    @NotNull
    private LocalDate dataNascimento;

    private String naturalidade;
    private String nacionalidade;

    @NotNull
    @Pattern(regexp ="[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}",
            message = "CPF precisa estar num formato válido")
    private String cpf;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @Setter(AccessLevel.NONE)
    private Instant criadoEm;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @Setter(AccessLevel.NONE)
    private Instant atualizadoEm;

    @PrePersist
    public void prePersist() {
        criadoEm = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        atualizadoEm = Instant.now();
    }





}
