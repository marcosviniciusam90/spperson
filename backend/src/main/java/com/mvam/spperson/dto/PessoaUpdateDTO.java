package com.mvam.spperson.dto;

import com.mvam.spperson.services.validation.PessoaUpdateValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@PessoaUpdateValid
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor()
public class PessoaUpdateDTO extends PessoaDTO {
}
