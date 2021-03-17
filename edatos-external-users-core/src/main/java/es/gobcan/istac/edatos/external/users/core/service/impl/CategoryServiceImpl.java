package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
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
        CategoryEntity category = categoryRepository.findOne(id);
        if (category == null) {
            throw new EDatosException(ServiceExceptionType.OPERATION_NOT_FOUND);
        }
        return category;
    }

    @Override
    public CategoryEntity findCategoryByCode(String code) {
        CategoryEntity category = categoryRepository.findByCode(code);
        if (category == null) {
            throw new EDatosException(ServiceExceptionType.OPERATION_CODE_NOT_FOUND, code);
        }
        return category;
    }

    @Override
    public CategoryEntity findCategoryByUrn(String urn) {
        CategoryEntity category = categoryRepository.findByUrn(urn);
        if (category == null) {
            throw new EDatosException(ServiceExceptionType.OPERATION_NOT_FOUND, urn);
        }
        return category;
    }

    @Override
    public List<CategoryEntity> findAllCategories() {
        return categoryRepository.findAll();
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
    public CategoryEntity createCategory(CategoryEntity category) {
        validateCategoryCodeUnique(category.getCode(), null);
        return categoryRepository.saveAndFlush(category);
    }

    @Override
    public CategoryEntity updateCategory(CategoryEntity category) {
        validateCategoryCodeUnique(category.getCode(), category.getId());
        return categoryRepository.saveAndFlush(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.delete(categoryId);
    }

    private void validateCategoryCodeUnique(String code, Long actualId) {
        if ((actualId == null && categoryRepository.existsByCode(code)) || (actualId != null && categoryRepository.existsByCodeAndIdNot(code, actualId))) {
            throw new EDatosException(ServiceExceptionType.OPERATION_ALREADY_EXIST_CODE_DUPLICATED, code);
        }
    }
}
