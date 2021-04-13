package es.gobcan.istac.edatos.external.users.core.service.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.errors.CustomParameterizedExceptionBuilder;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;

@Component
public class LoginValidator extends AbstractValidator<String> {

    @Autowired
    private ExternalUserRepository externalUserRepository;

    public void validate(String login) {
        if (StringUtils.isNotBlank(login)) {
            checkExistsUsuarioWithLogin(login);
            checkUsuarioIsActivated(login);
        } else {
            throw new CustomParameterizedExceptionBuilder().message("El campo login no puede estar vacío").code(ErrorConstants.LOGIN_VACIO).build();
        }
    }

    private void checkExistsUsuarioWithLogin(String login) {
        if (!externalUserRepository.existsByEmailIgnoreCase(login)) {
            throw new CustomParameterizedExceptionBuilder().message(String.format("El usuario con el login %s no existe", login)).code(ErrorConstants.LOGIN_USUARIO_NO_EXISTE).build();
        }
    }

    private void checkUsuarioIsActivated(String login) {
        if (!externalUserRepository.existsByEmailIgnoreCaseAndDeletionDateIsNull(login)) {
            throw new CustomParameterizedExceptionBuilder().message(String.format("El usuario con el login %s se encuentra desactivado", login)).code(ErrorConstants.LOGIN_USUARIO_DESACTIVADO).build();
        }
    }

}
