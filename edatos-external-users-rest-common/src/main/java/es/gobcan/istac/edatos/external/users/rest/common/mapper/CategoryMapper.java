package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.rest.common.dto.CategoryDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class, InternationalStringVOMapper.class})
public abstract class CategoryMapper implements EntityMapper<CategoryDto, CategoryEntity> {
    @Override
    public abstract CategoryDto toDto(CategoryEntity entity);

    @Override
    public abstract CategoryEntity toEntity(CategoryDto dto);
}
