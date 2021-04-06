package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalUserDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class, ExternalUserRepository.class})
public abstract class ExternalUserMapper implements EntityMapper<ExternalUserDto, ExternalUserEntity> {
    @Override
    @Mapping(target = "password", ignore = true)
    public abstract ExternalUserDto toDto(ExternalUserEntity entity);

    @Override
    @Mapping(target = "password", ignore = true)
    public abstract ExternalUserEntity toEntity(ExternalUserDto dto);
}
