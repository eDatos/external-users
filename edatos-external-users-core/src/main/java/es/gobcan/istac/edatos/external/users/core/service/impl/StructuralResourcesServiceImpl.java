package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.ItemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;
import es.gobcan.istac.edatos.external.users.core.domain.vo.LocalisedStringVO;
import es.gobcan.istac.edatos.external.users.core.service.EDatosApisLocator;
import es.gobcan.istac.edatos.external.users.core.service.StructuralResourcesService;

@Service
public class StructuralResourcesServiceImpl implements StructuralResourcesService {

    private final Logger log = LoggerFactory.getLogger(StructuralResourcesServiceImpl.class);

    private final EDatosApisLocator eDatosApisLocator;

    public StructuralResourcesServiceImpl(EDatosApisLocator eDatosApisLocator) {
        this.eDatosApisLocator = eDatosApisLocator;
    }

    @Override
    @Cacheable(cacheManager = "requestScopedCacheManager", cacheNames = "externalCategories")
    public List<ExternalCategoryEntity> getCategories() {
        log.info("Making request to SRM: GET categories");
        Categories c = eDatosApisLocator.srmExternal().findCategories("ISTAC", "SDMXStatSubMatDomainsWD1", "01.000", null, null, null, null);
        List<ExternalCategoryEntity> categories = new ArrayList<>();
        List<ItemResource> resources = c.getCategories();
        for (ItemResource resource : resources) {
            categories.add(itemResourceToExternalCategory(resource));
        }
        return categories;
    }

    private ExternalCategoryEntity itemResourceToExternalCategory(ItemResource resource) {
        ExternalCategoryEntity externalCategory = new ExternalCategoryEntity();

        externalCategory.setCode(resource.getId());
        externalCategory.setNestedCode(resource.getNestedId());
        externalCategory.setUrn(resource.getUrn());
        externalCategory.setName(internationalStringToVO(resource.getName()));

        return externalCategory;
    }

    private InternationalStringVO internationalStringToVO(InternationalString resource) {
        InternationalStringVO internationalString = new InternationalStringVO();
        internationalString.getTexts()
                .addAll(resource.getTexts().stream().map(localisedString -> new LocalisedStringVO(localisedString.getValue(), localisedString.getLang())).collect(Collectors.toSet()));
        return internationalString;
    }
}
