package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.rest.common.dto.FavoriteDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class, ExternalUserAccountMapper.class, CategoryMapper.class, OperationMapper.class})
public interface FavoriteMapper extends EntityMapper<FavoriteDto, FavoriteEntity> {

    @Override
    @Mapping(target = "email", source = "externalUser.email")
    FavoriteDto toDto(FavoriteEntity entity);

    @Override
    @Mapping(target = "externalUser", source = "email")
    FavoriteEntity toEntity(FavoriteDto dto);
}
