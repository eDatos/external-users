package es.gobcan.istac.edatos.external.users.core.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;

@Repository
public interface ExternalOperationRepository extends AbstractExternalItemRepository<ExternalOperationEntity> {

    List<ExternalOperationEntity> findByExternalCategoryUrnIn(List<String> urns);
}
