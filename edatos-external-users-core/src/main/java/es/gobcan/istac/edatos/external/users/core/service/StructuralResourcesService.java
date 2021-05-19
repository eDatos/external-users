package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;

public interface StructuralResourcesService {
    List<ExternalCategoryEntity> getCategories();
}
