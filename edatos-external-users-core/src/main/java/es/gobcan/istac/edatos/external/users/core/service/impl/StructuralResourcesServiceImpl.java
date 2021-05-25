package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.List;

import org.siemac.metamac.rest.structural_resources.v1_0.domain.Categories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;
import es.gobcan.istac.edatos.external.users.core.mapper.ItemResourceMapper;
import es.gobcan.istac.edatos.external.users.core.service.EDatosApisLocator;
import es.gobcan.istac.edatos.external.users.core.service.StructuralResourcesService;

@Service
public class StructuralResourcesServiceImpl implements StructuralResourcesService {

    private final Logger log = LoggerFactory.getLogger(StructuralResourcesServiceImpl.class);

    private final EDatosApisLocator eDatosApisLocator;
    /**
     * Amount of resources that can be obtained in a request.
     */
    private static final String LIMIT = String.valueOf(Integer.MAX_VALUE);

    private final ItemResourceMapper itemResourceMapper;

    public StructuralResourcesServiceImpl(EDatosApisLocator eDatosApisLocator, ItemResourceMapper itemResourceMapper) {
        this.eDatosApisLocator = eDatosApisLocator;
        this.itemResourceMapper = itemResourceMapper;
    }

    @Override
    @Cacheable(cacheManager = "requestScopedCacheManager", cacheNames = "externalCategories")
    public List<ExternalCategoryEntity> getCategories() {
        log.info("Making request to Structural Resources Manager to GET all the categories");
        Categories categories = eDatosApisLocator.srmExternal().findCategories("~all", "~all", "~all", null, null, LIMIT, null);
        return itemResourceMapper.toExternalCategoryEntities(categories);
    }

    @Override
    public Page<ExternalCategoryEntity> getCategories(Pageable pageable) {
        log.info("Making request to Structural Resources Manager to GET a page of categories");
        Categories categories = eDatosApisLocator.srmExternal().findCategories("~all", "~all", "~all", null, null, String.valueOf(pageable.getPageSize()), String.valueOf(pageable.getOffset()));
        List<ExternalCategoryEntity> externalCategories = itemResourceMapper.toExternalCategoryEntities(categories);
        return new PageImpl<>(externalCategories, pageable, categories.getTotal().longValue());
    }
}
