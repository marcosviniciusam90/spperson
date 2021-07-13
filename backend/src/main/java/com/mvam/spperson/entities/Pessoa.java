package com.mvam.spperson.entities;

import lombok.*;

import javax.persistence.*;
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

    private String nome;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    private String email;

    private LocalDate dataNascimento;

    private String naturalidade;
    private String nacionalidade;

    private String cpf;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
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
