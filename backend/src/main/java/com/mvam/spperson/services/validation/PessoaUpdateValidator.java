package com.mvam.spperson.services.validation;

import com.mvam.spperson.dto.PessoaUpdateDTO;
import com.mvam.spperson.entities.Pessoa;
import com.mvam.spperson.repositories.PessoaRepository;
import com.mvam.spperson.resources.exceptions.ErroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PessoaUpdateValidator implements ConstraintValidator<PessoaUpdateValid, PessoaUpdateDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PessoaRepository repository;

    @Override
    public void initialize(PessoaUpdateValid ann) {
        // not necessary
    }

    @Override
    public boolean isValid(PessoaUpdateDTO dto, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        Map<String, String> uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long pessoaId = Long.parseLong(uriVars.get("id"));

        List<ErroDTO.Campo> list = new ArrayList<>();

       Optional<Pessoa> pessoa = repository.findByCpf(dto.getCpf());
        if(pessoa.isPresent() && pessoaId != pessoa.get().getId()) {
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
