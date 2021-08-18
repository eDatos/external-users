package es.gobcan.istac.edatos.external.users.web.security.filter;

import java.io.IOException;
import es.gobcan.istac.edatos.external.users.core.security.SecurityUtils;
import es.gobcan.istac.edatos.external.users.web.security.provider.TokenProvider;
import io.jsonwebtoken.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final TokenProvider tokenProvider;

    public JWTAuthorizationFilter(AuthenticationManager authManager, TokenProvider tokenProvider) {
        super(authManager);
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String jwt = SecurityUtils.resolveToken(req);

        if (StringUtils.hasText(jwt)) {
            Jws<Claims> jwtClaims = this.tokenProvider.validateToken(jwt);
            if (jwtClaims != null) {
                Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                renewTicketIfNecessary(res, jwtClaims, authentication);
            } else {
                SecurityContextHolder.clearContext();
            }

        }
        chain.doFilter(req, res);
    }

    private void renewTicketIfNecessary(ServletResponse servletResponse, Jws<Claims> jwtClaims, Authentication authentication) {

        if (tokenProvider.checkRenewTicketIsRequired(jwtClaims)) {
            String renewedJwt = tokenProvider.createToken(authentication, false);
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.addHeader(SecurityUtils.JHI_AUTHENTICATIONTOKEN, renewedJwt);
            logger.debug("Renewed token JWT: " + renewedJwt);
        }
    }
}
