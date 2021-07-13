package com.mvam.spperson.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_permissao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permissao {

    @Id
    private Long id;

    private String descricao;
}
