package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.rest.common.dto.CategoryDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class, InternationalStringVOMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDto, CategoryEntity> {

    @Override
    CategoryDto toDto(CategoryEntity entity);

    @Override
    CategoryEntity toEntity(CategoryDto dto);
}
