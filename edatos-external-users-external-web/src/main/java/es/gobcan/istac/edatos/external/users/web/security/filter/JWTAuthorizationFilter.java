package es.gobcan.istac.edatos.external.users.web.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import es.gobcan.istac.edatos.external.users.web.security.provider.TokenProvider;
import io.jsonwebtoken.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JHI_AUTHENTICATIONTOKEN = "jhi-authenticationtoken";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    public JWTAuthorizationFilter(AuthenticationManager authManager, TokenProvider tokenProvider) {
        super(authManager);
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String jwt = resolveToken(req);

        if (StringUtils.hasText(jwt)) {
            Jws<Claims> jwtClaims = this.tokenProvider.validateToken(jwt);
            if (jwtClaims != null) {
                Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                renewTicketIfNecessary(res, jwtClaims, authentication);
            }

        }
        chain.doFilter(req, res);
    }

    private void renewTicketIfNecessary(ServletResponse servletResponse, Jws<Claims> jwtClaims, Authentication authentication) {

        if (tokenProvider.checkRenewTicketIsRequired(jwtClaims)) {
            String renewedJwt = tokenProvider.createToken(authentication, false);
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.addHeader(JHI_AUTHENTICATIONTOKEN, renewedJwt);
            logger.debug("Renewed token JWT: " + renewedJwt);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        // Header
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7, bearerToken.length());
        }
        // Cookie
        if (request.getCookies() != null) {
            Optional<Cookie> tokenCookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(JHI_AUTHENTICATIONTOKEN)).findFirst();
            if (tokenCookie.isPresent()) {
                return tokenCookie.get().getValue();
            }
        }

        return null;
    }
}
