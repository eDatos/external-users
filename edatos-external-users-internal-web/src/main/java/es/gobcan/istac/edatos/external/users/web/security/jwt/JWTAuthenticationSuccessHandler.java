package es.gobcan.istac.edatos.external.users.web.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import es.gobcan.istac.edatos.external.users.core.config.Constants;
import es.gobcan.istac.edatos.external.users.web.config.ApplicationProperties;
import io.github.jhipster.config.JHipsterProperties;

public class JWTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String TOKEN = "token";
    public static final String AUTHENTICATIONTOKEN = "authentication_token_internal";
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
        Cookie cookie = new Cookie(AUTHENTICATIONTOKEN, jwt);
        cookie.setSecure(env.acceptsProfiles(Constants.SPRING_PROFILE_ENV));
        cookie.setMaxAge((int) tokenValidityInSeconds);
        cookie.setHttpOnly(false);
        cookie.setPath(getCookiePath());
        response.addCookie(cookie);

        // For evict JSESSIONID, invalidate the session of CASFilter
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        handle(request, response, authentication);
    }

    private String getCookiePath() {
        if (StringUtils.isBlank(applicationProperties.getEndpoint().getAppUrl())) {
            return ROOT_PATH;
        }

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(applicationProperties.getEndpoint().getAppUrl()).build();
        String path = uriComponents.getPath();

        if (StringUtils.isBlank(path)) {
            return ROOT_PATH;
        }

        return StringUtils.prependIfMissing(path, ROOT_PATH);
    }
}
