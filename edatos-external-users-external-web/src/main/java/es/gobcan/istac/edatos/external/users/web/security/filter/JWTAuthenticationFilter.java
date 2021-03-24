package es.gobcan.istac.edatos.external.users.web.security.filter;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ExternalUserRole;
import es.gobcan.istac.edatos.external.users.rest.common.dto.LoginDto;
import es.gobcan.istac.edatos.external.users.web.security.LoginPasswordAuthenticationProvider;
import es.gobcan.istac.edatos.external.users.web.security.jwt.TokenProvider;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationProvider authenticationManager;

    private final TokenProvider tokenProvider;

    private final ObjectMapper mapper;

    public JWTAuthenticationFilter(AuthenticationProvider authenticationManager, TokenProvider tokenProvider, ObjectMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.mapper = mapper;

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

        TokenResponse tokenResponse = new TokenResponse(token, auth.getName());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);

        mapper.writeValue(res.getWriter(), tokenResponse);
        res.getWriter().flush();
    }

    private Authentication createAuthentication(LoginDto login) {
        Collection<? extends GrantedAuthority> authorities = Collections.singleton((GrantedAuthority) () -> ExternalUserRole.USER.name());

        User principal = new User(login.getLogin(), login.getPassword(), authorities);

        return new UsernamePasswordAuthenticationToken(principal, login.getLogin(), authorities);
    }
}
