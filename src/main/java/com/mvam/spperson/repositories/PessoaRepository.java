package com.mvam.spperson.repositories;

import com.mvam.spperson.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByIdNotLikeAndCpf(Long id, String cpf);
}
