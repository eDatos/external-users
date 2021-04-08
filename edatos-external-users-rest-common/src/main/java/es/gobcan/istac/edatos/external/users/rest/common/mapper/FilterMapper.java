package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FilterEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.FilterDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class, ExternalUserMapper.class})
public abstract class FilterMapper implements EntityMapper<FilterDto, FilterEntity> {

    @Autowired
    private ExternalUserRepository externalUserRepository;

    @Override
    @Mapping(target = "resourceName", ignore = true)
    public abstract FilterDto toDto(FilterEntity entity);

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
    @Mapping(target = "externalUser", source = "externalUser.id", qualifiedByName = "externalUserFromId")
    public abstract FilterEntity toEntity(FilterDto dto);

    @Named("externalUserFromId")
    public ExternalUserEntity externalUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        return externalUserRepository.findOne(id);
    }
}
