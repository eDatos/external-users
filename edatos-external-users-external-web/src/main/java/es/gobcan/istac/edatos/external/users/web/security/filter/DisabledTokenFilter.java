package es.gobcan.istac.edatos.external.users.web.security.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import es.gobcan.istac.edatos.external.users.core.repository.DisabledTokenRepository;

@Component
public class DisabledTokenFilter implements Filter {
    
    private final DisabledTokenRepository disabledTokenRepository;

    public DisabledTokenFilter(DisabledTokenRepository disabledTokenRepository) {
        this.disabledTokenRepository = disabledTokenRepository;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader("Authorization");
        token = (token != null && token.startsWith("Bearer ")) ? token.substring(7, token.length()) : token;
        
        boolean thereIsAToken = StringUtils.hasText(token);
        boolean theTokenIsDisabled = disabledTokenRepository.exists(token);
        if(thereIsAToken && theTokenIsDisabled) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}
