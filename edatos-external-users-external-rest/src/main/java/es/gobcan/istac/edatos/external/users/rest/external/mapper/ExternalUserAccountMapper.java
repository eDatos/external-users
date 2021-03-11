package es.gobcan.istac.edatos.external.users.rest.external.mapper;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;

import es.gobcan.istac.edatos.external.users.rest.external.dto.ExternalUserAccountDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.EntityMapper;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class})
public abstract class ExternalUserAccountMapper implements EntityMapper<ExternalUserAccountDto, ExternalUserEntity> {

    @Autowired
    private ExternalUserRepository externalUserRepository;

    public ExternalUserEntity getUserFromEmail(String email) {
        return externalUserRepository.findOneByEmail(email).orElse(null);
    }
}
