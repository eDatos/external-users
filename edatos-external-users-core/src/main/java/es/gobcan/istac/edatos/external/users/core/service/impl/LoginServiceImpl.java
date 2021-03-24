package es.gobcan.istac.edatos.external.users.core.service.impl;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.errors.CustomParameterizedExceptionBuilder;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.security.SecurityUtils;
import es.gobcan.istac.edatos.external.users.core.service.LoginService;
import es.gobcan.istac.edatos.external.users.core.service.validator.LoginValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private ExternalUserRepository externalUserRepository;

    @Autowired
    private LoginValidator loginValidator;

    @Override
    public Optional<ExternalUserEntity> authenticate(String login, String password) {
        log.debug("Request to authenticate a user: {}", login);

        loginValidator.validate(login);

        //@formatter:off
        Optional<ExternalUserEntity> usuarioToAuthenticate = externalUserRepository.findOneByEmailIgnoreCaseAndDeletionDateIsNull(login)
            .filter(usuario -> SecurityUtils.passwordEncoderMatches(password, usuario.getPassword()));
        //@formatter:on

        return usuarioToAuthenticate;

    }
}
