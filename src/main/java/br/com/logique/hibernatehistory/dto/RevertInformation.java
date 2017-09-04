package br.com.logique.hibernatehistory.dto;

import lombok.Data;

/**
 * @author victor.
 */
@Data
public class RevertInformation {

    private String name;
    private Long id;
    private Long revision;

}
