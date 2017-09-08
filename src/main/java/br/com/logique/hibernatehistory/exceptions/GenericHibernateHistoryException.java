package br.com.logique.hibernatehistory.exceptions;

/**
 * @author victor.
 */
public class GenericHibernateHistoryException extends RuntimeException {

    public GenericHibernateHistoryException(Exception ex) {
        super(ex);
    }

    public GenericHibernateHistoryException(String message) {
        super(message);
    }

}
