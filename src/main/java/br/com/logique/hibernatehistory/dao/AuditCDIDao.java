package br.com.logique.hibernatehistory.dao;

import br.com.logique.hibernatehistory.business.util.AuditUtil;

import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManager;

/**
 * Created by Joilson on 14/02/2018.
 */
public class AuditCDIDao extends AbstractAuditDao {

    @Override
    public EntityManager getEntityManager() {
        return CDI.current().select(EntityManager.class).get();
    }

}
