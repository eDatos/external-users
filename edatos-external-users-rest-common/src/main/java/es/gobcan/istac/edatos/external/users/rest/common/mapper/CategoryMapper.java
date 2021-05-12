package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalItemEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalCategoryRepository;
import es.gobcan.istac.edatos.external.users.core.service.FavoriteService;
import es.gobcan.istac.edatos.external.users.core.service.StructuralResourcesService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.CategoryDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalItemDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.config.AuditingMapperConfig;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", config = AuditingMapperConfig.class, uses = {GenericMapperResolver.class, InternationalStringVOMapper.class, ExternalItemMapper.class})
public abstract class CategoryMapper implements EntityMapper<CategoryDto, CategoryEntity> {

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    StructuralResourcesService structuralResourcesService;

    @Autowired
    ExternalCategoryRepository externalCategoryRepository;

    @Override
    @Mapping(target = "subscribers", expression = "java(favoriteService.getCategorySubscribers().getOrDefault(entity.getId(), 0L))")
    @Mapping(target = "resources", source = "entity.externalItems")
    public abstract CategoryDto toDto(CategoryEntity entity);

    @Override
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "externalItems", source = "dto.resources", qualifiedByName = "getExternalItemEntitiesFromUrn")
    public abstract CategoryEntity toEntity(CategoryDto dto);

    @Named("getExternalItemEntitiesFromUrn")
    public Set<ExternalItemEntity> getExternalItemEntitiesFromUrn(List<ExternalItemDto> resources) {
        List<String> urns = resources.stream().map(ExternalItemDto::getUrn).filter(Objects::nonNull).collect(Collectors.toList());
        List<ExternalCategoryEntity> inDbExternalCategories = externalCategoryRepository.findAll();
        // @formatter:off
        return structuralResourcesService.getCategories().stream()
                // pick only the selected external categories
                .filter(externalCategory -> urns.contains(externalCategory.getUrn()))
                // if the external category was already in db, pick that one; otherwise create a new one (this avoid problems with unique constraints and duplication)
                .map(newExternalCategory -> inDbExternalCategories.stream()
                                                                  .filter(inDbExternalCategory -> Objects.equals(inDbExternalCategory.getUrn(), newExternalCategory.getUrn()))
                                                                  .findAny()
                                                                  .orElse(newExternalCategory))
                .collect(Collectors.toSet());
        // @formatter:on
    }
}
