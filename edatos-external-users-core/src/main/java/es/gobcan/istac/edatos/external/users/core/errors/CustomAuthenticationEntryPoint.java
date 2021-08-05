package es.gobcan.istac.edatos.external.users.core.errors;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        super.commence(request, response, authException);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("external-users-external");
        super.afterPropertiesSet();
    }
}