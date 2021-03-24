package es.gobcan.istac.edatos.external.users.web.security.filter;

import es.gobcan.istac.edatos.external.users.core.config.Constants;
import es.gobcan.istac.edatos.external.users.rest.common.dto.LoginDto;
import es.gobcan.istac.edatos.external.users.web.config.ApplicationProperties;
import es.gobcan.istac.edatos.external.users.web.security.provider.TokenProvider;
import io.github.jhipster.config.JHipsterProperties;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String JHI_AUTHENTICATIONTOKEN = "authenticationtoken";
    public static final String ROOT_PATH = "/";

    private final AuthenticationProvider authenticationManager;

    private final TokenProvider tokenProvider;

    private final ObjectMapper mapper;

    private final JHipsterProperties jHipsterProperties;
    private long tokenValidityInSeconds;

    private final ApplicationProperties applicationProperties;

    private final Environment env;

    public JWTAuthenticationFilter(AuthenticationProvider authenticationManager, TokenProvider tokenProvider, ObjectMapper mapper, JHipsterProperties jHipsterProperties,
            ApplicationProperties applicationProperties, Environment env) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.mapper = mapper;
        this.jHipsterProperties = jHipsterProperties;
        this.applicationProperties = applicationProperties;
        this.env = env;

        this.tokenValidityInSeconds = jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();

        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            LoginDto login = new ObjectMapper().readValue(req.getInputStream(), LoginDto.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        String token = tokenProvider.createToken(auth, false);

        Cookie cookie = new Cookie(JHI_AUTHENTICATIONTOKEN, token);
        cookie.setSecure(env.acceptsProfiles(Constants.SPRING_PROFILE_ENV));
        cookie.setMaxAge((int) tokenValidityInSeconds);
        cookie.setHttpOnly(false);
        cookie.setPath(getCookiePath());
        res.addCookie(cookie);

        TokenResponse tokenResponse = new TokenResponse(token, auth.getName());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);

        mapper.writeValue(res.getWriter(), tokenResponse);
        res.getWriter().flush();
    }

    private String getCookiePath() {
        if (org.apache.commons.lang3.StringUtils.isBlank(applicationProperties.getEndpoint().getAppUrl())) {
            return ROOT_PATH;
        }

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(applicationProperties.getEndpoint().getAppUrl()).build();
        String path = uriComponents.getPath();

        if (org.apache.commons.lang3.StringUtils.isBlank(path)) {
            return ROOT_PATH;
        }

        return StringUtils.prependIfMissing(path, ROOT_PATH);
    }

}
