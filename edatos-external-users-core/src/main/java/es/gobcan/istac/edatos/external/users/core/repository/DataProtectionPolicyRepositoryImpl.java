package es.gobcan.istac.edatos.external.users.core.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.gobcan.istac.edatos.external.users.core.domain.DataProtectionPolicyEntity;

public class DataProtectionPolicyRepositoryImpl implements DetachEntityRepositoryCustom<DataProtectionPolicyEntity> {

    @PersistenceContext
    private EntityManager entityManager;
    
    public void detachEntity(DataProtectionPolicyEntity entity) {
        entityManager.detach(entity);
    }
}
