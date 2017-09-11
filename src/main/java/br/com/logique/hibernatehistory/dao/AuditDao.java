package br.com.logique.hibernatehistory.dao;

import br.com.logique.hibernatehistory.business.util.GenericObjectConverter;
import br.com.logique.hibernatehistory.business.util.ReflectionUtil;
import br.com.logique.hibernatehistory.dto.History;
import br.com.logique.hibernatehistory.dto.Register;
import br.com.logique.hibernatehistory.exceptions.GenericHibernateHistoryException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.reflections.Reflections;

import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Version;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by italo.alan on 01/09/2017.
 */
@Slf4j
public class AuditDao {

    public Object findEntityAtRevision(Class<Object> clazz, Long id, Long revision) {
        AuditReader reader = AuditReaderFactory.get(getEntityManager());
        Object entity;
        try {
            entity =  reader.find(clazz, id, revision);
        } catch (javax.persistence.NoResultException e) {
            throw new GenericHibernateHistoryException(e);
        }

        return entity;
    }

    public History findRevisionById(Class<Object> clazz, Long id, Long revisao) {
        AuditReader reader = AuditReaderFactory.get(getEntityManager());
        Object[] objectColunas;
        try {
            objectColunas = (Object[]) reader.createQuery().forRevisionsOfEntity(clazz, true, true).
                    addProjection(AuditEntity.revisionNumber()).
                    addProjection(AuditEntity.revisionType()).
                    addProjection(AuditEntity.revisionProperty("timestamp")).
                    add(AuditEntity.id().eq(id)).
                    add(AuditEntity.revisionNumber().eq(revisao)).
                    getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            objectColunas = null;
        }

        History history = convertToHistory(clazz, id, reader, objectColunas);
        return history;
    }

    public Register findById(Class classType, Long id) {
        Object o = getEntityManager().find(classType, id);
        Register register = null;
        if (o != null) {
            register = convertToRegister(o);
        }
        return register;
    }

    public List<History> listRevisionsByEntityId(Class<Object> clazz, Long id) {
        AuditReader reader = AuditReaderFactory.get(getEntityManager());

        List resultadoQuery = reader.createQuery().forRevisionsOfEntity(clazz, true, true).
                addProjection(AuditEntity.revisionNumber()).
                addProjection(AuditEntity.revisionType()).
                addProjection(AuditEntity.revisionProperty("timestamp")).
                add(AuditEntity.id().eq(id)).getResultList();

        List<History> retorno = new ArrayList<>();

        resultadoQuery.forEach(o -> {

            History history = convertToHistory(clazz, id, reader, (Object[]) o);
            retorno.add(history);

        });
        return retorno;
    }

    public List<Register> all(Class<Object> clazz) {

        Query query = getEntityManager().createQuery("from " + clazz.getName());
        List lista = query.getResultList();
        List<Register> registros = new ArrayList<>();
        lista.forEach(o -> registros.add(convertToRegister(o)));

        return registros;
    }

    private Register convertToRegister(Object o) {
        Long recordId = ReflectionUtil.getAllFieldsAnnotedBy(o.getClass(), Id.class).stream()
                .map(field -> (Long) ReflectionUtil.getFieldValue(field, o)).findFirst().orElse(null);

        return Register.builder()
                .id(recordId)
                .description(getDescriptionFromGenericObject(o))
                .object(GenericObjectConverter.objectInfo(o)).build();
    }

    private String getDescriptionFromGenericObject(Object o) {
        String description;

        try {
            description = o.toString();
        } catch (Exception e) {
            description = e.getMessage();
        }

        return description;
    }

    public Object doRevert(Object entidade, Long id) {
        incrementVersionAttributeIfPresent(entidade, id);
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entidade);
        entityManager.getTransaction().commit();
        entityManager.close();
        return entidade;
    }

    private History convertToHistory(Class<Object> clazz, Long id, AuditReader reader, Object[] objectColunas) {
        final int INDICE_REVISAO = 0;
        final int INDICE_REVISAO_TYPE = 1;
        final int INDICE_DATA = 2;
        Long revisao = (Long) objectColunas[INDICE_REVISAO];
        Object objectBanco = reader.find(clazz, id, revisao);

        if (objectBanco == null) {
            throw new GenericHibernateHistoryException("Object at revision requested was not found on database");
        }

        String description = getDescriptionFromGenericObject(objectBanco);

        return History.builder().
                date(new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(new Date((Long) objectColunas[INDICE_DATA]))).
                description(description).
                revision(revisao).
                revisionType(((RevisionType) objectColunas[INDICE_REVISAO_TYPE]).getRepresentation().intValue()).
                object(GenericObjectConverter.objectInfo(objectBanco)).build();
    }

    private void incrementVersionAttributeIfPresent(Object entidade, Long id) {
        Reflections reflections = new Reflections();

        Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(Audited.class);
        allClasses.forEach(aClass -> {

            if (aClass.getName().equals(entidade.getClass().getName())) {

                List<Field> fields = ReflectionUtil.getAllFieldsAnnotedBy(entidade.getClass(), Version.class);

                if (fields != null && fields.size() > 0) {

                    Object obj = getEntityManager().find(entidade.getClass(), id);
                    fields.forEach(field -> {
                        long versao = (long) ReflectionUtil.getFieldValue(field, obj);
                        ReflectionUtil.setFieldValue(field, entidade, versao);
                    });

                }
            }
        });
    }

    private EntityManager getEntityManager() {
        return CDI.current().select(EntityManager.class).get();
    }



}
