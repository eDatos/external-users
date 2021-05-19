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
import es.gobcan.istac.edatos.external.users.core.service.validator.CategoryValidator;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final QueryUtil queryUtil;
    private final CategoryValidator categoryValidator;

    public CategoryServiceImpl(CategoryRepository categoryRepository, QueryUtil queryUtil, CategoryValidator categoryValidator) {
        this.categoryRepository = categoryRepository;
        this.queryUtil = queryUtil;
        this.categoryValidator = categoryValidator;
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
        categoryValidator.checkCategoriesWithSubscribers(tree, categoryRepository.findAll());
        tree = categoryRepository.save(new HashSet<>(tree));
        categoryRepository.flush();
        return tree;
    }

    @Override
    public Optional<CategoryEntity> delete(Long id) {
        if (id != null) {
            CategoryEntity category = categoryRepository.getOne(id);
            if (category != null) {
                categoryValidator.checkCategoriesWithSubscribers(category);
                categoryRepository.delete(category);
                return Optional.of(category);
            }
        }
        return Optional.empty();
    }
}
