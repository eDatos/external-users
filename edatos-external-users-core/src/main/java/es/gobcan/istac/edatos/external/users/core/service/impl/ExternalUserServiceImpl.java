package es.gobcan.istac.edatos.external.users.core.service.impl;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.errors.CustomParameterizedExceptionBuilder;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.security.SecurityUtils;
import es.gobcan.istac.edatos.external.users.core.service.ExternalUserService;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ExternalUserServiceImpl implements ExternalUserService {

    @Autowired
    private ExternalUserRepository externalUserRepository;

    @Autowired
    private QueryUtil queryUtil;

    @Override
    public ExternalUserEntity create(ExternalUserEntity user) {
        return externalUserRepository.saveAndFlush(user);
    }

    @Override
    public void update(String firstName, String apellido1, String apellido2, String email) {
        externalUserRepository.findOneByEmail(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            user.setName(firstName);
            user.setSurname1(apellido1);
            user.setSurname2(apellido2);
            user.setEmail(email);
        });
    }

    @Override
    public ExternalUserEntity update(ExternalUserEntity user) {
        return externalUserRepository.saveAndFlush(user);
    }

    @Override
    public ExternalUserEntity delete(String email) {
        ExternalUserEntity usuario = externalUserRepository.findOneByEmailAndDeletionDateIsNull(email)
            .orElseThrow(() -> new CustomParameterizedExceptionBuilder().message("Usuario no válido").code(ErrorConstants.USUARIO_NO_VALIDO).build());
        usuario.setDeletionDate(Instant.now());
        usuario.setDeletedBy(SecurityUtils.getCurrentUserLogin());
        return externalUserRepository.saveAndFlush(usuario);
    }

    @Override
    public ExternalUserEntity recover(String email) {
        ExternalUserEntity usuario = externalUserRepository.findOneByEmailAndDeletionDateIsNotNull(email)
            .orElseThrow(() -> new CustomParameterizedExceptionBuilder().message("Usuario no válido").code(ErrorConstants.ENTIDAD_NO_ENCONTRADA, email).build());
        usuario.setDeletionDate(null);
        usuario.setDeletedBy(null);
        return externalUserRepository.saveAndFlush(usuario);
    }

}
