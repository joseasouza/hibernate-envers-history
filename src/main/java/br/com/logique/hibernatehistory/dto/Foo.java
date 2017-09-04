package br.com.logique.hibernatehistory.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author victor.
 */
@Data
@Builder
public class Foo {

    private String nome;
    private Long id;
    private Double quantidade;

}
