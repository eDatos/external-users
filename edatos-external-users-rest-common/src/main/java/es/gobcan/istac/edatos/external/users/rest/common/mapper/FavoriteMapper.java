package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.FavoriteDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class, ExternalUserAccountMapper.class, CategoryMapper.class, OperationMapper.class})
public abstract class FavoriteMapper implements EntityMapper<FavoriteDto, FavoriteEntity> {

    @Autowired
    private ExternalUserRepository externalUserRepository;

    @Override
    @Mapping(target = "externalUser", source = "externalUser", qualifiedByName = "externalUserToBaseDto")
    public abstract FavoriteDto toDto(FavoriteEntity entity);

    @Override
    @Mapping(target = "externalUser", source = "externalUser.id", qualifiedByName = "externalUserFromId")
    public abstract FavoriteEntity toEntity(FavoriteDto dto);

    @Named("externalUserFromId")
    public ExternalUserEntity externalUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        return externalUserRepository.findOne(id);
    }
}
