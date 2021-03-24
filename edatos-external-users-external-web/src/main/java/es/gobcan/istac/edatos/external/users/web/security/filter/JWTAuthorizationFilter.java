package es.gobcan.istac.edatos.external.users.web.security.filter;

import java.io.IOException;
import java.util.ArrayList;

import es.gobcan.istac.edatos.external.users.web.config.JHipsterExtraProperties;
import io.github.jhipster.config.JHipsterProperties;
import io.jsonwebtoken.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JHI_AUTHENTICATIONTOKEN = "authenticationtoken";

    private JHipsterProperties jHipsterProperties;

    public JWTAuthorizationFilter(AuthenticationManager authManager, JHipsterProperties jHipsterProperties) {
        super(authManager);
        this.jHipsterProperties = jHipsterProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(JHI_AUTHENTICATIONTOKEN);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String bearerToken = request.getHeader(JHI_AUTHENTICATIONTOKEN);

        if (bearerToken != null) {
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                new UsernamePasswordAuthenticationToken(bearerToken.substring(7, bearerToken.length()), null, new ArrayList<>());
            }

            return null;
        }

        return null;
    }
}
