package es.gobcan.istac.edatos.external.users.core.service;

import java.util.Set;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;

public interface StructuralResourcesService {
    Set<ExternalCategoryEntity> getCategories();
}
