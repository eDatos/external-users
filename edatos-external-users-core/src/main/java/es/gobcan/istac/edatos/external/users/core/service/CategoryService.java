package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;

public interface CategoryService {

    CategoryEntity findCategoryById(Long id);
    CategoryEntity findCategoryByCode(String code);
    CategoryEntity findCategoryByUrn(String urn);
    List<CategoryEntity> findAllCategories();
    Page<CategoryEntity> find(String query, Pageable pageable);
    Page<CategoryEntity> find(DetachedCriteria criteria, Pageable pageable);
    CategoryEntity createCategory(CategoryEntity operation);
    CategoryEntity updateCategory(CategoryEntity operation);
    void deleteCategory(Long operationId);
}
