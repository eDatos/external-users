package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;

public interface CategoryService {

    CategoryEntity findCategoryById(Long id);
    Page<CategoryEntity> find(String query, Pageable pageable);
    Page<CategoryEntity> find(DetachedCriteria criteria, Pageable pageable);
    List<CategoryEntity> getTree();
    List<CategoryEntity> updateTree(List<CategoryEntity> tree);
    CategoryEntity createCategory(CategoryEntity category);
    CategoryEntity updateCategory(CategoryEntity category);
    Optional<CategoryEntity> delete(Long id);
}
