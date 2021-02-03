package es.gobcan.istac.edatos.external.users.internal.rest.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;
import es.gobcan.istac.edatos.external.users.internal.rest.dto.UsuarioDto;
import es.gobcan.istac.edatos.external.users.internal.rest.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class})
public interface UsuarioMapper extends EntityMapper<UsuarioDto, UsuarioEntity> {
}
