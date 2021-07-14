package com.mvam.spperson.services.validation;

import com.mvam.spperson.dto.PessoaInsertDTO;
import com.mvam.spperson.entities.Pessoa;
import com.mvam.spperson.repositories.PessoaRepository;
import com.mvam.spperson.resources.exceptions.ErroDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PessoaInsertValidator implements ConstraintValidator<PessoaInsertValid, PessoaInsertDTO> {

    @Autowired
    private PessoaRepository repository;

    @Override
    public void initialize(PessoaInsertValid ann) {
        // not necessary
    }

    @Override
    public boolean isValid(PessoaInsertDTO dto, ConstraintValidatorContext context) {

        List<ErroDTO.Campo> list = new ArrayList<>();

        Optional<Pessoa> pessoa = repository.findByCpf(dto.getCpf());
        if(pessoa.isPresent()) {
            list.add(new ErroDTO.Campo("cpf", "Ja existe uma pessoa cadastrada com este CPF"));
        }

        for (ErroDTO.Campo e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNome())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
