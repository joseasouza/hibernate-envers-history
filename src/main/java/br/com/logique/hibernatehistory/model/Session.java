package br.com.logique.hibernatehistory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author victor.
 */
@Data
@NoArgsConstructor
public class Session implements Serializable {

    public static final String HIBERNATE_ENVERS_HISTORY_SESSION = "hibernate.envers.history.session";

    private boolean isLogged;

}
