package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;

public interface StructuralResourcesService {
    List<ExternalCategoryEntity> getCategories();
    Page<ExternalCategoryEntity> getCategories(Pageable pageable);
}
