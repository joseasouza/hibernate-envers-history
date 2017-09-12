package br.com.logique.hibernatehistory.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author victor.
 */
@Data
@Builder
public class History {

    private Long revision;
    private Integer revisionType;
    private String date;
    private String description;
    private String author;
    private Object object;

}
