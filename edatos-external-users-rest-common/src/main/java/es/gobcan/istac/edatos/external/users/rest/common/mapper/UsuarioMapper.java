package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;
import es.gobcan.istac.edatos.external.users.core.repository.UsuarioRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.UsuarioDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.config.AuditingMapperConfig;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", config = AuditingMapperConfig.class, uses = {GenericMapperResolver.class})
public abstract class UsuarioMapper implements EntityMapper<UsuarioDto, UsuarioEntity> {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioEntity getUserFromLogin(String login) {
        return usuarioRepository.findOneByLogin(login).orElse(null);
    }
}
