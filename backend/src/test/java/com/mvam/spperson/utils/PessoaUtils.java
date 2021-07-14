package com.mvam.spperson.utils;

import com.github.javafaker.Faker;
import com.mvam.spperson.dto.PessoaDTO;
import com.mvam.spperson.dto.PessoaInsertDTO;
import com.mvam.spperson.entities.Pessoa;
import com.mvam.spperson.entities.Sexo;

import java.time.ZoneId;

public class PessoaUtils {

    private static final Faker FAKER = Faker.instance();

    private static final String CPF_REGEX = "[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}";

    public static Pessoa createPessoa(Long codigo) {
        return Pessoa.builder()
                .id(codigo)
                .nome(FAKER.superhero().name())
                .sexo(Sexo.MASCULINO)
                .email(FAKER.internet().emailAddress())
                .dataNascimento(FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .naturalidade(FAKER.address().cityName())
                .nacionalidade(FAKER.address().country())
                .cpf(FAKER.regexify(CPF_REGEX))
                .build();

    }

    public static PessoaDTO createPessoaDTO(Long codigo) {
        return PessoaDTO.builder()
                .id(codigo)
                .nome(FAKER.superhero().name())
                .sexo(Sexo.MASCULINO.toString())
                .email(FAKER.internet().emailAddress())
                .dataNascimento(FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .naturalidade(FAKER.address().cityName())
                .nacionalidade(FAKER.address().country())
                .cpf(FAKER.regexify(CPF_REGEX))
                .build();

    }

    public static PessoaInsertDTO createPessoaInsertDTO(Long codigo) {
        return PessoaInsertDTO.builder()
                .id(codigo)
                .nome(FAKER.superhero().name())
                .sexo(Sexo.MASCULINO.toString())
                .email(FAKER.internet().emailAddress())
                .dataNascimento(FAKER.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .naturalidade(FAKER.address().cityName())
                .nacionalidade(FAKER.address().country())
                .cpf(FAKER.regexify(CPF_REGEX))
                .build();

    }
}
