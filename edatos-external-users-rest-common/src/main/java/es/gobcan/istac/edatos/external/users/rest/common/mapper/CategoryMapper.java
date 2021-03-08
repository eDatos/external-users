package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.rest.common.dto.CategoryDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class})
public interface CategoryMapper extends EntityMapper<CategoryDto, CategoryEntity> {

    @Override
    CategoryDto toDto(CategoryEntity entity);

    /**
     * <code>defaultExpression</code> assigns a value in case the source is null. In
     * this case, mapstruct will generate the next piece of code:
     *
     * <pre>
     * if (dto.getName() != null) {
     *     filterEntity.setName(dto.getName());
     * } else {
     *     filterEntity.setName(dto.getResourceName());
     * }
     * </pre>
     */
    @Override
    CategoryEntity toEntity(CategoryDto dto);
}
