package com.mvam.spperson.services;

import com.mvam.spperson.dto.PessoaDTO;
import com.mvam.spperson.entities.Pessoa;
import com.mvam.spperson.entities.Sexo;
import com.mvam.spperson.mappers.PessoaMapper;
import com.mvam.spperson.repositories.PessoaRepository;
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
    public Page<PessoaDTO> findAllByNomeContaining(String nome, PageRequest pageRequest) {
        Page<Pessoa> pageList = pessoaRepository.findByNomeContainingIgnoreCase(nome, pageRequest);
        return pageList.map(PESSOA_MAPPER::entityToDTO);
    }

    @Transactional(readOnly = true)
    public PessoaDTO findById(Long id) {
        Optional<Pessoa> optionalPessoa = pessoaRepository.findById(id);
        Pessoa pessoa = optionalPessoa.orElseThrow(() -> new RecursoNaoEncontradoException(id));
        return PESSOA_MAPPER.entityToDTO(pessoa);
    }

    @Transactional
    public PessoaDTO create(PessoaDTO dto) {
        Pessoa entity = new Pessoa();
        copyDtoToEntity(dto, entity);

        entity = pessoaRepository.save(entity);
        return PESSOA_MAPPER.entityToDTO(entity);
    }

    @Transactional
    public PessoaDTO update(Long id, PessoaDTO dto) {
        try {
            Pessoa entity = pessoaRepository.getOne(id);
            copyDtoToEntity(dto, entity);

            entity = pessoaRepository.save(entity);
            return PESSOA_MAPPER.entityToDTO(entity);
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

    private void copyDtoToEntity(PessoaDTO dto, Pessoa entity) {
        entity.setNome(dto.getNome());
        entity.setSexo(Sexo.valueOf(dto.getSexo()));
        entity.setEmail(dto.getEmail());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setNaturalidade(dto.getNaturalidade());
        entity.setNacionalidade(dto.getNacionalidade());
        entity.setCpf(dto.getCpf());
    }
}
