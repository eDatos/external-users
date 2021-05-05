package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.ItemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;
import es.gobcan.istac.edatos.external.users.core.domain.vo.LocalisedStringVO;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.core.service.EDatosApisLocator;
import es.gobcan.istac.edatos.external.users.core.service.StructuralResourcesService;

@Service
public class StructuralResourcesServiceImpl implements StructuralResourcesService {

    private final Logger log = LoggerFactory.getLogger(StructuralResourcesServiceImpl.class);

    EDatosApisLocator eDatosApisLocator;

    public StructuralResourcesServiceImpl(EDatosApisLocator eDatosApisLocator) {
        this.eDatosApisLocator = eDatosApisLocator;
    }

    @Override
    public List<ExternalCategoryEntity> getCategories() {
        log.info("Making request to SRM: GET categories");
        Categories c = eDatosApisLocator.srmExternal().findCategories("ISTAC", "SDMXStatSubMatDomainsWD1", "01.000", null, null, null, null);
        List<ExternalCategoryEntity> categories = new ArrayList<>();
        List<ItemResource> resources = c.getCategories();
        for (ItemResource resource : resources) {
            categories.add(itemResourceToCategory(resource, resources, categories));
        }
        return categories;
    }

    // TODO(EDATOS-3357): This is a mapping between to classes... it would be a great idea to use MapStruct for this,
    //  the dependency needs to be moved from rest-common upwards to core tho.
    private ExternalCategoryEntity itemResourceToCategory(ItemResource resource, List<ItemResource> resources, List<ExternalCategoryEntity> categories) {
        ExternalCategoryEntity externalCategory = new ExternalCategoryEntity();

        if (resource.getParent() != null) {
            // First, check if the parent has been converted before
            Optional<ExternalCategoryEntity> categoryParent = categories.stream().filter(r -> Objects.equals(r.getUrn(), resource.getParent())).findAny();
            if (categoryParent.isPresent()) {
                externalCategory.setParent(categoryParent.get());
            } else {
                // Otherwise, find the parent and convert it.
                // We are taking for granted that the URNs we get from structural-resources are all unique.
                Optional<ItemResource> parent = resources.stream().filter(r -> Objects.equals(r.getUrn(), resource.getParent())).findAny();
                if (parent.isPresent()) {
                    externalCategory.setParent(itemResourceToCategory(parent.get(), resources, categories));
                } else {
                    // TODO(EDATOS-3357): EDatosException should not be used for internal errors (?)
                    throw new EDatosException(ServiceExceptionType.ITEM_RESOURCE_PARENT_NOT_FOUND);
                }
            }
        }

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
