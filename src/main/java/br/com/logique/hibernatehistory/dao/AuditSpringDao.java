package br.com.logique.hibernatehistory.dao;

import br.com.logique.hibernatehistory.business.util.AuditUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Created by italo.alan on 01/09/2017.
 */
@Component
public class AuditSpringDao extends AbstractAuditDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    @Transactional
    public Object doRevert(Object entidade, Long id) {
        EntityManager entityManager = getEntityManager();
        AuditUtil.incrementVersionAttributeIfPresent(entidade, entityManager, id);
        entityManager.merge(entidade);
        return entidade;
    }

}
