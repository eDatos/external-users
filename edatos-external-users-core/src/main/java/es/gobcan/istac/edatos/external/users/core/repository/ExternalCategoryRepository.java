package es.gobcan.istac.edatos.external.users.core.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;

@Repository
public interface ExternalCategoryRepository extends AbstractExternalItemRepository<ExternalCategoryEntity> {
    Optional<ExternalCategoryEntity> getByUrn(String urn);
}
