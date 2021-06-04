package es.gobcan.istac.edatos.external.users.web.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import es.gobcan.istac.edatos.external.users.core.repository.DisabledTokenRepository;

@Component
public class DisabledTokenFilter implements Filter {
    
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JHI_AUTHENTICATIONTOKEN = "authenticationtoken";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    
    private final DisabledTokenRepository disabledTokenRepository;

    public DisabledTokenFilter(DisabledTokenRepository disabledTokenRepository) {
        this.disabledTokenRepository = disabledTokenRepository;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) request);
        
        boolean thereIsATokenAndItIsDisabled = StringUtils.hasText(token) && disabledTokenRepository.exists(token);
        if(thereIsATokenAndItIsDisabled) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

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
