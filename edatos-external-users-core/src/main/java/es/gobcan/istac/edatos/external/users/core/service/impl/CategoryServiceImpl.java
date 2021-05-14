package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
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
    public CategoryEntity findCategoryById(Long id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public Page<CategoryEntity> find(String query, Pageable pageable) {
        DetachedCriteria criteria = queryUtil.queryToCategoryCriteria(query, pageable);
        return find(criteria, pageable);
    }

    @Override
    public Page<CategoryEntity> find(DetachedCriteria criteria, Pageable pageable) {
        return categoryRepository.findAll(criteria, pageable);
    }

    @Override
    public List<CategoryEntity> getTree() {
        return categoryRepository.getByParentIsNull();
    }

    @Override
    public List<CategoryEntity> updateTree(List<CategoryEntity> tree) {
        tree = categoryRepository.save(new HashSet<>(tree));
        categoryRepository.flush();
        return tree;
    }

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        return categoryRepository.saveAndFlush(category);
    }

    @Override
    public CategoryEntity updateCategory(CategoryEntity category) {
        return categoryRepository.saveAndFlush(category);
    }

    @Override
    public Optional<CategoryEntity> delete(Long id) {
        if (id != null) {
            return categoryRepository.deleteById(id);
        }
        return Optional.empty();
    }
}
