package es.gobcan.istac.edatos.external.users.core.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.Categories;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.ItemResource;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;

@Mapper(componentModel = "spring", uses = {InternationalStringMapper.class})
public abstract class ItemResourceMapper {

    public List<ExternalCategoryEntity> toExternalCategoryEntities(Categories categories) {
        return categories.getCategories().stream().map(this::toExternalCategoryEntity).collect(Collectors.toList());
    }

    @Mapping(target = "nestedCode", source = "nestedId")
    @Mapping(target = "code", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "optLock", ignore = true)
    @Mapping(target = "categories", ignore = true)
    protected abstract ExternalCategoryEntity toExternalCategoryEntity(ItemResource itemResource);
}
