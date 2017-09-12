package br.com.logique.hibernatehistory.anotacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação responsável por marcar quais entidades devem ser exibidas no menu lateral
 * Created by italo.alan on 11/09/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityAudited {

    /**
     * Nome de exibição da entidade para mostrar no Menu lateral do frontEnd
     */
    String display() default "";

    /**
     * Colunas para exibir nos detalhes da entidade
     */
    Columns[] columns() default {};
}

