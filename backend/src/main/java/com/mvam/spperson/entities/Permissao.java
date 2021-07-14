package com.mvam.spperson.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_permissao")
public class Permissao {

    @Id
    private Long id;

    @EqualsAndHashCode.Include
    private String descricao;
}
