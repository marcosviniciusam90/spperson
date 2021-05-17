package com.mvam.spperson.services;

import com.mvam.spperson.dto.PessoaDTO;
import com.mvam.spperson.entities.Pessoa;
import com.mvam.spperson.mappers.PessoaMapper;
import com.mvam.spperson.repositories.PessoaRepository;
import com.mvam.spperson.services.exceptions.PessoaComMesmoCPFException;
import com.mvam.spperson.services.exceptions.RecursoNaoEncontradoException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaService {

    private static final PessoaMapper PESSOA_MAPPER = PessoaMapper.INSTANCE;
    private final PessoaRepository pessoaRepository;

    @Transactional(readOnly = true)
    public Page<PessoaDTO> findAllPaged(PageRequest pageRequest) {
        Page<Pessoa> pageList = pessoaRepository.findAll(pageRequest);
        return pageList.map(PESSOA_MAPPER::entityToDTO);
    }

    @Transactional(readOnly = true)
    public PessoaDTO findById(Long id) {
        Optional<Pessoa> optionalPessoa = pessoaRepository.findById(id);
        Pessoa pessoa = optionalPessoa.orElseThrow(() -> new RecursoNaoEncontradoException(id));
        return PESSOA_MAPPER.entityToDTO(pessoa);
    }

    @Transactional
    public PessoaDTO create(PessoaDTO pessoaDTO) {
        checkIfExistsPersonWithTheSameCPFInCreate(pessoaDTO.getCpf());
        Pessoa pessoa = PESSOA_MAPPER.dtoToEntity(pessoaDTO);
        pessoa = pessoaRepository.save(pessoa);
        return PESSOA_MAPPER.entityToDTO(pessoa);
    }

    @Transactional
    public PessoaDTO update(Long id, PessoaDTO pessoaDTO) {
        checkIfExistsPersonWithTheSameCPFInUpdate(id, pessoaDTO.getCpf());
        try {
            Pessoa previousPessoa = pessoaRepository.getOne(id);

            Pessoa pessoa = PESSOA_MAPPER.dtoToEntity(pessoaDTO);
            pessoa.setId(previousPessoa.getId());
            pessoa.setCriadoEm(previousPessoa.getCriadoEm());
            pessoa = pessoaRepository.save(pessoa);
            return PESSOA_MAPPER.entityToDTO(pessoa);
        } catch (EntityNotFoundException ex) {
            throw new RecursoNaoEncontradoException(id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            pessoaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new RecursoNaoEncontradoException(id);
        }
    }

    private void checkIfExistsPersonWithTheSameCPFInCreate(String cpf) {
        if(pessoaRepository.existsByCpf(cpf)) {
            throw new PessoaComMesmoCPFException(cpf);
        }
    }

    private void checkIfExistsPersonWithTheSameCPFInUpdate(Long id, String cpf) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findByCpf(cpf);

        if(pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();

            if (!id.equals(pessoa.getId())) {
                throw new PessoaComMesmoCPFException(cpf);
            }
        }
    }
}
