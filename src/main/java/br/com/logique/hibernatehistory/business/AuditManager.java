package br.com.logique.hibernatehistory.business;

import br.com.logique.hibernatehistory.annotation.EntityAudited;
import br.com.logique.hibernatehistory.dao.AuditDao;
import br.com.logique.hibernatehistory.dto.Entity;
import br.com.logique.hibernatehistory.dto.History;
import br.com.logique.hibernatehistory.dto.Register;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author victor.
 */
public class AuditManager {

    Set<Class<?>> allClasses;

    private AuditDao auditoriaDao;
    private Reflections reflections;

    public AuditManager() {
        this.auditoriaDao = new AuditDao();
        reflections = new Reflections();
        setClassesAuditadas();
    }

    public List<History> listarRevisoesDaEntidade(String clazz, Long id) throws ClassNotFoundException {
        List<History> revisoes = this.auditoriaDao.listRevisionsByEntityId(this.getClassForName(clazz), id);
        return revisoes;
    }

    public Register findById(String clazz, Long id) throws ClassNotFoundException {
        return auditoriaDao.findById(this.getClassForName(clazz), id);
    }

    public History buscarEntidadePorRevisao(String clazz, Long id, Long revisao) throws ClassNotFoundException {
        History entidade = this.auditoriaDao.findRevisionById(this.getClassForName(clazz), id, revisao);
        return entidade;
    }

    public List<Register> todos(String clazz) throws ClassNotFoundException {
        List<Register> entidades = this.auditoriaDao.all(this.getClassForName(clazz));
        return entidades;
    }

    public Object reverter(String clazz, Long id, Long revisao) throws ClassNotFoundException {
        Object entityAtRev = this.auditoriaDao.findEntityAtRevision(this.getClassForName(clazz), id, revisao);
        Object entidade = this.auditoriaDao.doRevert(entityAtRev, id);
        return entidade;
    }

    public List<Entity> getNomesClassesAuditadas() {
        List<Entity> classes = new ArrayList<>();
        allClasses.stream().forEach(aClass -> {
            EntityAudited entityAudited = aClass.getAnnotation(EntityAudited.class);
            classes.add(Entity.builder().
                    name(aClass.getSimpleName()).
                    displayName(entityAudited.display()).build());
        });
        classes.sort(Comparator.comparing(Entity::getName));
        return classes;
    }

    private Class getClassForName(String nameClass) throws ClassNotFoundException {
        return this.allClasses.stream().filter(cls -> cls.getSimpleName().equals(nameClass)).findFirst().orElseThrow(ClassNotFoundException::new);
    }

    private void setClassesAuditadas() {
        this.allClasses = this.reflections.getTypesAnnotatedWith(EntityAudited.class);
    }

}
