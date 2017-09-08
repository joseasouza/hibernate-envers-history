package br.com.logique.hibernatehistory.business.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Remove referencias ciclicas contidas em um objeto.
 * 
 * @author victor
 *
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
		if (Iterable.class.isAssignableFrom(campo.getType())) {
			info.put(campo.getName(), processarObjetoIteravel((Iterable<Object>) campo.get(objeto)));
		} else {
			info.put(campo.getName(), Objects.toString(campo.get(objeto)));
		}
		campo.setAccessible(false);
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
