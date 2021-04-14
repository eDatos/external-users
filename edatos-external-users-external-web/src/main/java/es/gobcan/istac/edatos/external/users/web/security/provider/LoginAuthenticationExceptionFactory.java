package es.gobcan.istac.edatos.external.users.web.security.provider;

import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.http.HttpStatus;

public class LoginAuthenticationExceptionFactory {

    public static LoginAuthenticationException instance(Throwable throwable) {
        if (throwable instanceof EDatosException) {
            return createFromEDatosException((EDatosException) throwable);
        }
        return createFromGenericException(throwable);
    }

    private static LoginAuthenticationException createFromEDatosException(EDatosException eDatosException) {
        LoginAuthenticationException loginException = new LoginAuthenticationException(eDatosException.getExceptionItems().get(0).getMessage(), eDatosException);
        loginException.setCode(eDatosException.getExceptionItems().get(0).getCode());
        loginException.setStatus(HttpStatus.BAD_REQUEST);
        return loginException;
    }

    private static LoginAuthenticationException createFromGenericException(Throwable genericException) {
        LoginAuthenticationException loginException = new LoginAuthenticationException(genericException.getMessage(), genericException);
        loginException.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return loginException;
    }

}
