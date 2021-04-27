package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;
import es.gobcan.istac.edatos.external.users.core.repository.CategoryRepository;
import es.gobcan.istac.edatos.external.users.core.service.CategoryService;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final QueryUtil queryUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, QueryUtil queryUtil) {
        this.categoryRepository = categoryRepository;
        this.queryUtil = queryUtil;
    }

    @Override
    public ExternalCategoryEntity findCategoryById(Long id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public Page<ExternalCategoryEntity> find(String query, Pageable pageable) {
        DetachedCriteria criteria = queryUtil.queryToCategoryCriteria(query, pageable);
        return find(criteria, pageable);
    }

    @Override
    public Page<ExternalCategoryEntity> find(DetachedCriteria criteria, Pageable pageable) {
        return categoryRepository.findAll(criteria, pageable);
    }

    @Override
    public List<ExternalCategoryEntity> getTree() {
        return categoryRepository.getByParentIsNull();
    }

    @Override
    public ExternalCategoryEntity createCategory(ExternalCategoryEntity category) {
        return categoryRepository.saveAndFlush(category);
    }

    @Override
    public ExternalCategoryEntity updateCategory(ExternalCategoryEntity category) {
        return categoryRepository.saveAndFlush(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.delete(categoryId);
    }
}
