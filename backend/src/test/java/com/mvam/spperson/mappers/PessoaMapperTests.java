package com.mvam.spperson.mappers;

import com.github.javafaker.Faker;
import com.mvam.spperson.dto.PessoaDTO;
import com.mvam.spperson.entities.Pessoa;
import org.junit.jupiter.api.Test;

import static com.mvam.spperson.utils.PessoaUtils.createPessoa;
import static com.mvam.spperson.utils.PessoaUtils.createPessoaDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PessoaMapperTests {

    private static final Faker FAKER = Faker.instance();
    private static final PessoaMapper PESSOA_MAPPER = PessoaMapper.INSTANCE;

    @Test
    void givenDTOThenMustConvertToEntity() {
        PessoaDTO pessoaDTO = createPessoaDTO(FAKER.number().randomNumber());
        Pessoa pessoa = PESSOA_MAPPER.dtoToEntity(pessoaDTO);

        assertConvertion(pessoa, pessoaDTO);
    }

    @Test
    void givenEntityThenMustConvertToDTO() {
        Pessoa pessoa = createPessoa(FAKER.number().randomNumber());
        PessoaDTO pessoaDTO = PESSOA_MAPPER.entityToDTO(pessoa);

        assertConvertion(pessoa, pessoaDTO);
    }

    private void assertConvertion(Pessoa pessoa, PessoaDTO pessoaDTO) {
        assertEquals(pessoa.getId(), pessoaDTO.getId());
        assertEquals(pessoa.getNome(), pessoaDTO.getNome());
        assertEquals(pessoa.getSexo().toString(), pessoaDTO.getSexo());
        assertEquals(pessoa.getEmail(), pessoaDTO.getEmail());
        assertEquals(pessoa.getDataNascimento(), pessoaDTO.getDataNascimento());
        assertEquals(pessoa.getNaturalidade(), pessoaDTO.getNaturalidade());
        assertEquals(pessoa.getNacionalidade(), pessoaDTO.getNacionalidade());
        assertEquals(pessoa.getCpf(), pessoaDTO.getCpf());
    }
}
