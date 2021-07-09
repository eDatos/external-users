package es.gobcan.istac.edatos.external.users.web.security.filter;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CaptchaFilter extends OncePerRequestFilter {
    
    @Context
    private HttpServletRequest  request;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        UriBuilder urlBuilder = UriBuilder.fromUri("http://localhost:8084/api/captcha/validate");
        for(String paramKey : Collections.list(req.getParameterNames())) {
            urlBuilder.queryParam(paramKey, req.getParameter(paramKey));
        }
        URI url = urlBuilder.build();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, boolean.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            if(response.getBody().booleanValue()) {
                chain.doFilter(req, res);
            } else {
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        } else {
            res.setStatus(response.getStatusCodeValue());
        }
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return !StringUtils.equalsAny(path, "/api/login", "/api/account/signup");
    }
}

