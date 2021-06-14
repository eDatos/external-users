package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

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
    private final EntityManager entityManager;

    public CategoryServiceImpl(CategoryRepository categoryRepository, QueryUtil queryUtil, CategoryValidator categoryValidator, EntityManager entityManager) {
        this.categoryRepository = categoryRepository;
        this.queryUtil = queryUtil;
        this.categoryValidator = categoryValidator;
        this.entityManager = entityManager;
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
        List<CategoryEntity> saved = categoryRepository.save(tree);

        // During development there was a bug that would cause the server to return stale data of the tree
        // structure. When the user drag and drops a node, the entire tree is updated. This would go as expected
        // until the tree was saved and the data was returned: it turns out that while it was correctly saved to
        // the database, Hibernate (for some reason) would return stale data of the tree. For example, if an
        // entire branch from the tree was dragged upwards in the hierarchy, Hibernate would return the new changes
        // plus the old ones, meaning we would have that branch duplicated (under the new AND the old parent).
        //
        // I think the problem comes from the Hibernate cache (not sure whether the first or second level one),
        // but I'm not sure yet, and so I can't pinpoint the exact issue that is causing this bug.
        //
        // So, for now, the fix comes as simple as flushing the changes to DB after saving and then updating
        // the tree from the database data (through calling refresh). It's a slow operation, for sure, but given that
        // the tree is not updated frequently it should not be that much of a bottleneck.
        entityManager.flush();
        for (CategoryEntity category : saved) {
            entityManager.refresh(category);
        }

        return saved;
    }

    @Override
    public Optional<CategoryEntity> delete(Long id) {
        if (id != null) {
            CategoryEntity category = categoryRepository.getOne(id);
            if (category != null) {
                categoryValidator.checkCategoriesWithSubscribers(category);
                categoryRepository.delete(category);
                category.getParent().removeChild(category);
                return Optional.of(category);
            }
        }
        return Optional.empty();
    }
}
