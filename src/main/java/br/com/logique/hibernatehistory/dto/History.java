package br.com.logique.hibernatehistory.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

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
    private Object object;

}
