package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ExternalUserRole;
import es.gobcan.istac.edatos.external.users.core.security.SecurityUtils;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalUserAccountDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class})
public abstract class ExternalUserAccountMapper implements EntityMapper<ExternalUserAccountDto, ExternalUserEntity> {

    @Autowired
    private ExternalUserRepository externalUserRepository;

    @Override
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    public abstract ExternalUserEntity toEntity(ExternalUserAccountDto dto);

    @Named("encodePassword")
    public String encodePassword(String unencryptedPassword) {
        return SecurityUtils.passwordEncoder(unencryptedPassword);
    }

    @AfterMapping
    public void setRoles(ExternalUserAccountDto dto, @MappingTarget ExternalUserEntity entity) {
        Set<ExternalUserRole> roles = new HashSet<>();
        roles.add(ExternalUserRole.USER);
        roles.stream().collect(Collectors.toSet());
        entity.setRoles(roles);
    }

    @Override
    @Mapping(target = "password", ignore = true)
    public abstract ExternalUserAccountDto toDto(ExternalUserEntity dto);

    public ExternalUserEntity getUserFromEmail(String email) {
        return externalUserRepository.findOneByEmail(email).orElse(null);
    }

}
