package br.com.logique.hibernatehistory.anotacao;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by italo.alan on 11/09/2017.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityAudited {

    String display() default "";
}

