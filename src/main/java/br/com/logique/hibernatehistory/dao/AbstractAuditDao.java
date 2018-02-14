package br.com.logique.hibernatehistory.dao;

import br.com.logique.hibernatehistory.business.util.AuditUtil;
import br.com.logique.hibernatehistory.business.util.GenericObjectConverter;
import br.com.logique.hibernatehistory.dto.History;
import br.com.logique.hibernatehistory.dto.Register;
import br.com.logique.hibernatehistory.exceptions.GenericHibernateHistoryException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Joilson on 14/02/2018.
 */
@Slf4j
public abstract class AbstractAuditDao {


    @Transactional
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

    @Transactional
    public History findRevisionById(Class<Object> clazz, Long id, Long revisao) {
        AuditReader reader = AuditReaderFactory.get(getEntityManager());
        Object[] objectColunas;
        try {

            AuditQuery auditQuery = reader.createQuery().forRevisionsOfEntity(clazz, true, true).
                    addProjection(AuditEntity.revisionNumber()).
                    addProjection(AuditEntity.revisionType()).
                    addProjection(AuditEntity.revisionProperty("timestamp"));

            String authorAttribute = AuditUtil.findAuthorAttribute();
            if (authorAttribute != null) {
                auditQuery.addProjection(AuditEntity.revisionProperty(authorAttribute));
            }

            objectColunas = (Object[]) auditQuery.
                    add(AuditEntity.id().eq(id)).
                    add(AuditEntity.revisionNumber().eq(revisao)).
                    getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            throw new GenericHibernateHistoryException(e);
        }

        History history = convertToHistory(clazz, id, reader, objectColunas);
        return history;
    }

    @Transactional
    public Register findById(Class classType, Long id) {
        Object o = getEntityManager().find(classType, id);
        Register register = null;
        if (o != null) {
            register = convertToRegister(o);
        }
        return register;
    }

    @Transactional
    public List<History> listRevisionsByEntityId(Class<Object> clazz, Long id) {
        AuditReader reader = AuditReaderFactory.get(getEntityManager());

        AuditQuery auditQuery = reader.createQuery().forRevisionsOfEntity(clazz, true, true).
                addProjection(AuditEntity.revisionNumber()).
                addProjection(AuditEntity.revisionType()).
                addProjection(AuditEntity.revisionProperty("timestamp"));

        String authorAttribute = AuditUtil.findAuthorAttribute();
        if (authorAttribute != null) {
            auditQuery.addProjection(AuditEntity.revisionProperty(authorAttribute));
        }

        List resultadoQuery = auditQuery.
                add(AuditEntity.id().eq(id)).getResultList();

        List<History> retorno = new ArrayList<>();

        resultadoQuery.forEach(o -> {

            History history = convertToHistory(clazz, id, reader, (Object[]) o);
            retorno.add(history);

        });
        return retorno;
    }

    @Transactional
    public List<Register> all(Class<Object> clazz) {

        Query query = getEntityManager().createQuery("from " + clazz.getName());
        List lista = query.getResultList();
        List<Register> registros = new ArrayList<>();
        lista.forEach(o -> registros.add(convertToRegister(o)));

        return registros;
    }

    public Object doRevert(Object entidade, Long id) {
        EntityManager entityManager = getEntityManager();
        AuditUtil.incrementVersionAttributeIfPresent(entidade, entityManager, id);
        entityManager.getTransaction().begin();
        entityManager.merge(entidade);
        entityManager.getTransaction().commit();
        entityManager.close();
        return entidade;
    }

    private Register convertToRegister(Object o) {
        Long recordId = AuditUtil.findRecordId(o);
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

    private History convertToHistory(Class<Object> clazz, Long id, AuditReader reader, Object[] objectColunas) {
        final int INDEX_REVISAO = 0;
        final int INDEX_REVISAO_TYPE = 1;
        final int INDEX_DATA = 2;
        Long revisao = (Long) objectColunas[INDEX_REVISAO];
        Object objectBanco = reader.find(clazz, id, revisao);

        if (objectBanco == null) {
            throw new GenericHibernateHistoryException("Object at revision requested was not found on database");
        }

        String description = getDescriptionFromGenericObject(objectBanco);

        return History.builder().
                date(new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(new Date((Long) objectColunas[INDEX_DATA]))).
                description(description).
                revision(revisao).
                revisionType(((RevisionType) objectColunas[INDEX_REVISAO_TYPE]).getRepresentation().intValue()).
                author(getAuthor(objectColunas)).
                object(GenericObjectConverter.objectInfo(objectBanco)).build();
    }

    private String getAuthor(Object[] objectColuna) {
        final int INDEX_AUTHOR = 3;
        String author = "";
        if (objectColuna.length > INDEX_AUTHOR) {
            author = Optional.ofNullable(objectColuna[INDEX_AUTHOR]).map(AuditUtil::getAuthorValue).orElse("");
        }
        return author;
    }

    public abstract EntityManager getEntityManager();
}
