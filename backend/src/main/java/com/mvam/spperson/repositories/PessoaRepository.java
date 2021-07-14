package com.mvam.spperson.repositories;

import com.mvam.spperson.entities.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByCpf(String cpf);
    Page<Pessoa> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
