package com.mvam.spperson.services;

import com.mvam.spperson.dto.PessoaDTO;
import com.mvam.spperson.repositories.PessoaRepository;
import com.mvam.spperson.services.exceptions.RecursoNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PessoaServiceIntegrationTests {

    @Autowired
    private PessoaService service;

    @Autowired
    private PessoaRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotal;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotal = 5L;
    }

    @Test
    void deleteShouldDeleteResourceWhenIdExists() {
        service.delete(existingId);
        Assertions.assertEquals(countTotal - 1, repository.count());
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    void findAllByNomeContainingShouldReturnPageWhenPage0Size10() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PessoaDTO> result = service.findAllByNomeContaining("", pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(page, result.getNumber());
        Assertions.assertEquals(size, result.getSize());
        Assertions.assertEquals(countTotal, result.getTotalElements());
    }

    @Test
    void findAllByNomeContainingShouldReturnEmptyPageWhenPageDoesNotExist() {
        int page = 50;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PessoaDTO> result = service.findAllByNomeContaining("", pageRequest);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void findAllByNomeContainingShouldReturnSortedPageWhenSortByName() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("nome"));
        Page<PessoaDTO> result = service.findAllByNomeContaining("", pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Antonina Rocha", result.getContent().get(0).getNome());
        Assertions.assertEquals("Antonio Geraldo", result.getContent().get(1).getNome());
        Assertions.assertEquals("Marcela Almeida", result.getContent().get(2).getNome());
    }

    @Test
    void findAllByNomeContainingShouldReturnSpecificUserWhenGivenName() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PessoaDTO> result = service.findAllByNomeContaining("Marcos", pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Marcos Vinicius", result.getContent().get(0).getNome());
    }


}
