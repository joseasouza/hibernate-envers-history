package br.com.logique.hibernatehistory.business.util;

import br.com.logique.hibernatehistory.annotation.Columns;
import br.com.logique.hibernatehistory.annotation.EntityAudited;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Remove referencias ciclicas contidas em um objeto.
 *
 * @author victor
 */
public class GenericObjectConverter {


    public static Map<String, Object> objectInfo(Object obj) {
        Map<String, Object> info = new TreeMap<>();
        List<Field> fields = ReflectionUtil.getAllFieldsFrom(obj.getClass());


        fields.stream().filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(f -> {
            try {
                processarCampo(f, obj, info);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });


        return info;
    }

    private static void processarCampo(Field campo, Object objeto, Map<String, Object> info) throws IllegalAccessException {
        campo.setAccessible(true);
        String displayColumn = campo.getName();

        Optional<EntityAudited> entidadeAuditada = getEntityAudited(campo);
        Optional<Columns> colunaOptional = getColunasOptional(campo, entidadeAuditada);

        if (colunaOptional.isPresent()) {
            displayColumn = colunaOptional.get().display();
        }
        if (isColunaSemAnotacaoOuColunaComAnotacaoComAtributoParaExibir(colunaOptional)) {
            if (Iterable.class.isAssignableFrom(campo.getType())) {
                info.put(displayColumn, processarObjetoIteravel((Iterable<Object>) campo.get(objeto)));
            } else {
                info.put(displayColumn, Objects.toString(campo.get(objeto)));
            }
            campo.setAccessible(false);
        }
    }

    private static boolean isColunaSemAnotacaoOuColunaComAnotacaoComAtributoParaExibir(Optional<Columns> colunaOptional) {
        return !colunaOptional.isPresent() || (colunaOptional.isPresent() && colunaOptional.get().show());
    }

    private static Optional<EntityAudited> getEntityAudited(Field campo) {
        try {
            return Optional.of(campo.getDeclaringClass().getAnnotation(EntityAudited.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static Optional<Columns> getColunasOptional(Field campo, Optional<EntityAudited> entidadeAuditada) {
        Optional<Columns> colunaOptional = Optional.empty();
        if (entidadeAuditada.isPresent()) {
            colunaOptional = Arrays.stream(entidadeAuditada.get().columns()).filter(column -> column.attributeName().equals(campo.getName())).findFirst();
        }
        return colunaOptional;
    }

    private static List<Object> processarObjetoIteravel(Iterable<Object> obj) {
        List<Object> objects = new ArrayList<>();
        Iterator iterator = obj.iterator();

        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next != null && Iterable.class.isAssignableFrom(next.getClass())) {
                objects.add(processarObjetoIteravel((Iterable<Object>) next));
            } else {
                objects.add(Objects.toString(next));
            }

        }
        return objects;
    }

}
