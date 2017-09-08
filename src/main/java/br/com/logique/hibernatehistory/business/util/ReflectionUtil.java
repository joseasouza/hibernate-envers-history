package br.com.logique.hibernatehistory.business.util;

import br.com.logique.hibernatehistory.exceptions.GenericHibernateHistoryException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author victor.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectionUtil {

    /**
     * Get all Fields of the presented classType and its class types that are above its hierarchy
     * @param classType classType type of class
     * @return returns the list of filtered fields
     */
    public static List<Field> getAllFieldsFrom(Class<?> classType) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(classType.getDeclaredFields()));

        if (classType.getSuperclass() != null) {
            fields.addAll(getAllFieldsFrom(classType.getSuperclass()));
        }

        return fields;
    }

    /**
     * Get all Fields of the presented class and their classTypes that are above their hierarchy
     * @param classType class that contains the fields
     * @param annotation annotation that will be used to filter the fields
     * @return returns the list of filtered fields
     */
    public static List<Field> getAllFieldsAnnotedBy(Class<?> classType, Class<? extends Annotation> annotation) {
        List<Field> allFields = getAllFieldsFrom(classType);
        List<Field> filteredFields = new ArrayList<>();
        allFields.stream().filter(field -> field.isAnnotationPresent(annotation)).forEach(field -> {
            filteredFields.add(field);
        });
        return filteredFields;
    }

    public static void setFieldValue(Field field, Object o, Object value) throws GenericHibernateHistoryException {

        field.setAccessible(true);
        try {
            field.set(o, value);
        } catch (IllegalAccessException e) {
            throw new GenericHibernateHistoryException(e);
        }
        field.setAccessible(false);
    }

    public static Object getFieldValue(Field field, Object o) throws GenericHibernateHistoryException {

        field.setAccessible(true);
        Object value;
        try {
            value = field.get(o);
        } catch (IllegalAccessException e) {
            throw new GenericHibernateHistoryException(e);
        }
        field.setAccessible(false);
        return value;
    }

}
