package es.gobcan.istac.edatos.external.users.web.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filters incoming requests and installs a Spring Security principal if a
 * header corresponding to a valid user is found.
 */
public class JWTFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider, AuthenticationManager authManager) {
        super(authManager);
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        String jwt = resolveToken(httpServletRequest);

        if (StringUtils.hasText(jwt)) {
            Jws<Claims> jwtClaims = this.tokenProvider.validateToken(jwt);
            if (jwtClaims != null) {
                Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                renewTicketIfNecessary(httpServletResponse, jwtClaims, authentication);
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * Expand the ticket expiration time if necessary (Renew the ticket)
     * Only if the current ticket has less than 30 minutes left.
     *
     * @param servletResponse
     * @param jwtClaims
     * @param authentication
     */
    private void renewTicketIfNecessary(ServletResponse servletResponse, Jws<Claims> jwtClaims, Authentication authentication) {

        if (tokenProvider.checkRenewTicketIsRequired(jwtClaims)) {
            Boolean rememberMe = false;
            String renewedJwt = tokenProvider.createToken(authentication, rememberMe);
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.addHeader(JWTAuthenticationSuccessHandler.JHI_AUTHENTICATIONTOKEN, renewedJwt);
            logger.debug("Renewed token JWT: " + renewedJwt);
        }

    }

    private String resolveToken(HttpServletRequest request) {
        // Header
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }

        return null;
    }
}
