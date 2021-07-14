package com.mvam.spperson.services;

import com.github.javafaker.Faker;
import com.mvam.spperson.dto.PessoaDTO;
import com.mvam.spperson.entities.Pessoa;
import com.mvam.spperson.mappers.PessoaMapper;
import com.mvam.spperson.repositories.PessoaRepository;
import com.mvam.spperson.services.exceptions.PessoaComMesmoCPFException;
import com.mvam.spperson.services.exceptions.RecursoNaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static com.mvam.spperson.utils.PessoaUtils.createPessoa;
import static com.mvam.spperson.utils.PessoaUtils.createPessoaDTO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTests {

    private static final Faker FAKER = Faker.instance();
    private static final PessoaMapper PESSOA_MAPPER = PessoaMapper.INSTANCE;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @Test
    void dadoPessoaEntaoDeveCriarComSucesso() {
        PessoaDTO pessoaDTO = createPessoaDTO(null);

        Pessoa pessoa = PESSOA_MAPPER.dtoToEntity(pessoaDTO);

        pessoaService.create(pessoaDTO);
        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    void dadoPessoaComCPFJaExistenteEntaoDeveLancarExcecaoAoCriar() {
        PessoaDTO pessoaDTO = createPessoaDTO(null);

        assertThrows(PessoaComMesmoCPFException.class, () -> pessoaService.create(pessoaDTO));

        verify(pessoaRepository, times(0)).save(any(Pessoa.class));
    }

    @Test
    void dadoIdDePessoaNaoExistenteEntaoDeveLancarExcecaoAoBuscarPeloId() {
        long id = FAKER.number().randomNumber();
        when(pessoaRepository.findById(id)).thenReturn(Optional.ofNullable(any(Pessoa.class)));

        assertThrows(RecursoNaoEncontradoException.class, () -> pessoaService.findById(id));
    }

    @Test
    void dadoPessoaEIdExistenteEntaoDeveAtualizarComSucesso() {
        Long id = FAKER.number().randomNumber();
        PessoaDTO pessoaDTO = createPessoaDTO(null);
        Pessoa previousPessoa = createPessoa(id);

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(previousPessoa));

        Pessoa pessoa = PESSOA_MAPPER.dtoToEntity(pessoaDTO);
        pessoa.setId(previousPessoa.getId());

        pessoaService.update(id, pessoaDTO);
        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    void dadoPessoaComCPFJaExistenteEntaoDeveLancarExcecaoAoAtualizar() {
        Long id = FAKER.number().randomNumber();
        PessoaDTO pessoaDTO = createPessoaDTO(null);
        Pessoa previousPessoa = createPessoa(id);

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(previousPessoa));

        assertThrows(PessoaComMesmoCPFException.class, () -> pessoaService.update(id, pessoaDTO));

        verify(pessoaRepository, times(0)).save(any(Pessoa.class));
    }

    @Test
    void dadoIdDePessoaExistenteEntaoDeveExcluirComSucesso() {
        Long id = FAKER.number().randomNumber();
        pessoaService.delete(id);
        verify(pessoaRepository, times(1)).deleteById(id);
    }

    @Test
    void dadoIdDePessoaNaoExistenteEntaoDeveLancarExcecaoAoExcluir() {
        Long id = FAKER.number().randomNumber();

        doThrow(EmptyResultDataAccessException.class).when(pessoaRepository).deleteById(id);
        assertThrows(RecursoNaoEncontradoException.class, () -> pessoaService.delete(id));
    }


}
