package com.mvam.spperson.dto;

import com.mvam.spperson.services.validation.PessoaInsertValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@PessoaInsertValid
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor()
public class PessoaInsertDTO extends PessoaDTO {
}
