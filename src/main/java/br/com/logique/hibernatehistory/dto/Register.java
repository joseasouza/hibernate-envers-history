package br.com.logique.hibernatehistory.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author victor.
 */
@Data
@Builder
public class Register {

    private Long id;
    private String description;
    private Object object;

}
