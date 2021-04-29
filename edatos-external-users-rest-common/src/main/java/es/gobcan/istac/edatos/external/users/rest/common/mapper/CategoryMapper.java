package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.core.service.FavoriteService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.CategoryDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.config.AuditingMapperConfig;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", config = AuditingMapperConfig.class, uses = {GenericMapperResolver.class, InternationalStringVOMapper.class, ExternalItemMapper.class})
public abstract class CategoryMapper implements EntityMapper<CategoryDto, CategoryEntity> {
    @Autowired
    FavoriteService favoriteService;

    @Override
    @Mapping(target = "subscribers", expression = "java(favoriteService.getCategorySubscribers().getOrDefault(entity.getId(), 0L))")
    @Mapping(target = "resources", source = "entity.externalItems")
    public abstract CategoryDto toDto(CategoryEntity entity);

    @Override
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "externalItems", ignore = true)
    public abstract CategoryEntity toEntity(CategoryDto dto);
}
