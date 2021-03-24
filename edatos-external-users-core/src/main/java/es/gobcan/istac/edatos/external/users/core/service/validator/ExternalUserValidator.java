package es.gobcan.istac.edatos.external.users.core.service.validator;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.errors.CustomParameterizedExceptionBuilder;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExternalUserValidator extends AbstractValidator<ExternalUserEntity> {

    @Autowired
    ExternalUserRepository externalUserRepository;

    public void checkEmailEnUso(ExternalUserEntity externalUser) {
        final String email = externalUser.getEmail();
        if (externalUserRepository.findOneByEmail(email).isPresent()) {
            final Optional<ExternalUserEntity> existingUserWithMail = Optional.ofNullable(externalUser.getId()).isPresent()
                    ? externalUserRepository.findOneByEmailAndIdNot(email, externalUser.getId())
                    : externalUserRepository.findOneByEmail(email);
            if (existingUserWithMail.isPresent()) {
                throw new CustomParameterizedExceptionBuilder().message(String.format("Email '%s' ya en uso", email)).code(ErrorConstants.USUARIO_EMAIL_EN_USO).build();
            }
        }

    }
}
