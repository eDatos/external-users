package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;

public interface CategoryService {

    ExternalCategoryEntity findCategoryById(Long id);
    Page<ExternalCategoryEntity> find(String query, Pageable pageable);
    Page<ExternalCategoryEntity> find(DetachedCriteria criteria, Pageable pageable);
    List<ExternalCategoryEntity> getTree();
    ExternalCategoryEntity createCategory(ExternalCategoryEntity operation);
    ExternalCategoryEntity updateCategory(ExternalCategoryEntity operation);
    void deleteCategory(Long operationId);
}
