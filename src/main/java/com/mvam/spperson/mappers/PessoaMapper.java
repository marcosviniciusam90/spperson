package com.mvam.spperson.mappers;

import com.mvam.spperson.dto.PessoaDTO;
import com.mvam.spperson.entities.Pessoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PessoaMapper {

    PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

    @Mapping(target = "atualizadoEm", ignore = true)
    Pessoa dtoToEntity(PessoaDTO categoryDTO);

    PessoaDTO entityToDTO(Pessoa entity);
}
