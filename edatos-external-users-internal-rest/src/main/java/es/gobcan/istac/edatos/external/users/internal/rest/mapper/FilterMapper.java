package es.gobcan.istac.edatos.external.users.internal.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.gobcan.istac.edatos.external.users.core.domain.FilterEntity;
import es.gobcan.istac.edatos.external.users.internal.rest.dto.FilterDto;
import es.gobcan.istac.edatos.external.users.internal.rest.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class, UsuarioMapper.class})
public interface FilterMapper extends EntityMapper<FilterDto, FilterEntity> {

    @Override
    @Mapping(target = "resourceName", ignore = true)
    @Mapping(target = "login", source = "user.login")
    FilterDto toDto(FilterEntity entity);

    /**
     * <code>defaultExpression</code> assigns a value in case the source is null. In
     * this case, mapstruct will generate the next piece of code:
     *
     * <pre>
     * if ( dto.getName() != null ) {
     *     filterEntity.setName( dto.getName() );
     * }
     * else {
     *     filterEntity.setName( dto.getResourceName() );
     * }
     * </pre>
     */
    @Override
    @Mapping(target = "name", defaultExpression = "java( dto.getResourceName() )")
    @Mapping(target = "lastAccessDate", defaultExpression = "java( java.time.Instant.now() )")
    @Mapping(target = "user", source = "login")
    FilterEntity toEntity(FilterDto dto);
}
