package br.com.logique.hibernatehistory.annotation;

/**
 * Anotações responsável por personalizar as colunas que serão exibidas no detalhe da entidade na auditoria,
 * permitindo renomear o nome da coluna para exibir um texto amigável ou ocultar a coluna nos detalhes.
 * Created by italo.alan on 12/09/2017.
 */
public @interface Columns {
    /**
     * Obrigatoriamente deve ser o mesmo nome do atributo
     */
    String attributeName() default "";

    /**
     * Nome de livre escolha para exibir no lugar no nome do atributo
     */
    String display() default "";

    /**
     * Marcador para exibir ou ocultar o atributo em tela
     */
    boolean show() default true;
}
