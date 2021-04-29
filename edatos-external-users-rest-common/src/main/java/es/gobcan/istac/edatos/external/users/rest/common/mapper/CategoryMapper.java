package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.rest.common.dto.CategoryDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.config.AuditingMapperConfig;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", config = AuditingMapperConfig.class, uses = {GenericMapperResolver.class, InternationalStringVOMapper.class})
public abstract class CategoryMapper implements EntityMapper<CategoryDto, CategoryEntity> {
    @Override
    public abstract CategoryDto toDto(CategoryEntity entity);

    @Override
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "externalItems", ignore = true)
    public abstract CategoryEntity toEntity(CategoryDto dto);
}
