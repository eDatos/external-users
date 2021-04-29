package es.gobcan.istac.edatos.external.users.core.repository;

import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;

@Repository
public interface ExternalOperationRepository extends AbstractExternalItemRepository<ExternalOperationEntity> {
}
