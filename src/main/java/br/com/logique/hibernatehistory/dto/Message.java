package br.com.logique.hibernatehistory.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author victor.
 */
@Data
@Builder
public class Message {

    private Integer code;
    private String message;

}
