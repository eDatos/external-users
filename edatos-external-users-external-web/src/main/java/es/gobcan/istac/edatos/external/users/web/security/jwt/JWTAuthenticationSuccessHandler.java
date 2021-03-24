package es.gobcan.istac.edatos.external.users.web.security.jwt;

import es.gobcan.istac.edatos.external.users.web.config.ApplicationProperties;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class JWTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String TOKEN = "token";
    public static final String JHI_AUTHENTICATIONTOKEN = "authenticationtoken";
    public static final String ROOT_PATH = "/";

    private Environment env;
    private long tokenValidityInSeconds;
    private TokenProvider tokenProvider;
    private ApplicationProperties applicationProperties;

    public JWTAuthenticationSuccessHandler(TokenProvider tokenProvider, JHipsterProperties jHipsterProperties, ApplicationProperties applicationProperties, Environment env) {
        this.tokenProvider = tokenProvider;
        this.tokenValidityInSeconds = jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.applicationProperties = applicationProperties;
        this.env = env;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        boolean rememberMe = false;
        String jwt = tokenProvider.createToken(authentication, rememberMe);

        // For evict JSESSIONID, invalidate the session of CASFilter
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        handle(request, response, authentication);
    }
}
