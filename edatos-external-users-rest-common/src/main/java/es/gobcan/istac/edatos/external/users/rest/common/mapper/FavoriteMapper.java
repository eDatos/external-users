package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.rest.common.dto.FavoriteDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class, UsuarioMapper.class})
public interface FavoriteMapper extends EntityMapper<FavoriteDto, FavoriteEntity> {

    @Override
    @Mapping(target = "login", source = "user.login")
    FavoriteDto toDto(FavoriteEntity entity);

    @Override
    @Mapping(target = "user", source = "login")
    FavoriteEntity toEntity(FavoriteDto dto);
}
