package com.mvam.spperson.repositories;

import com.mvam.spperson.entities.Pessoa;
import com.mvam.spperson.utils.PessoaUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
class PessoaRepositoryTests {

    @Autowired
    private PessoaRepository repository;

    private long existingId;
    private long nonExistingId;
    private long counTotal;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        counTotal = 5L;
    }

    @Test
    void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        Pessoa entity = PessoaUtils.createPessoa(null);

        entity = repository.save(entity);

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals(counTotal +1, entity.getId());
    }

    @Test
    void saveShouldPersistWithoutAutoIncrementWhenIdIsSet() {
        Pessoa entity = PessoaUtils.createPessoa(existingId);

        entity = repository.save(entity);

        Assertions.assertEquals(existingId, entity.getId());
    }

    @Test
    void findByIdShouldReturnAPresentOptionalObjectWhenIdExists() {
        Optional<Pessoa> result = repository.findById(existingId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    void findByIdShouldReturnAEmptyOptionalObjectWhenIdDoesNotExist() {
        Optional<Pessoa> result = repository.findById(nonExistingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(existingId);
        Optional<Pessoa> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }

    @Test
    void findByCpfShouldReturnAPresentOptionalObjectWhenIdExists () {
        Optional<Pessoa> result = repository.findByCpf("899.317.520-92");
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    void findByCpfShouldReturnAEmptyOptionalObjectWhenIdDoesNotExist () {
        Optional<Pessoa> result = repository.findByCpf("899.317.520-93");
        Assertions.assertFalse(result.isPresent());
    }
}
