package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;

public interface CategoryService {

    CategoryEntity findCategoryById(Long id);
    Page<CategoryEntity> find(String query, Pageable pageable);
    Page<CategoryEntity> find(DetachedCriteria criteria, Pageable pageable);
    List<CategoryEntity> getTree();
    CategoryEntity createCategory(CategoryEntity operation);
    CategoryEntity updateCategory(CategoryEntity operation);
    void deleteCategory(Long operationId);
}
