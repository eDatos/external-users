package es.gobcan.istac.edatos.external.users;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import es.gobcan.istac.edatos.external.users.core.config.MetadataProperties;
import es.gobcan.istac.edatos.external.users.core.service.HtmlService;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;
import es.gobcan.istac.edatos.external.users.web.config.ApplicationProperties;

@Controller
public class DefaultController {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private MetadataConfigurationService metadataService;
    
    @Autowired
    private MetadataProperties metadataProperties;

    @Autowired
    private HtmlService htmlService;

    private final Logger log = LoggerFactory.getLogger(DefaultController.class);

    private String faviconUrl;

    @PostConstruct
    public void init() {
        // TODO EDATOS-3215 y EDATOS-3266 Pendiente de cambio : this.faviconUrl = this.metadataService.retrieveFaviconUrl();
        this.faviconUrl = metadataService.findProperty(applicationProperties.getMetadata().getMetamacFaviconUrlKey());
    }

    @RequestMapping(value = {"", "/index.html", "/**/{path:[^\\.]*}"})
    @SuppressWarnings("unchecked")
    public ModelAndView index(HttpServletRequest request) {
        log.debug("DefaultController: Contextpath = {}  ServletPath = {}", request.getContextPath(), request.getServletPath());
        Map<String, Object> model = new HashMap<>();
        model.put("endpoint", applicationProperties.getEndpoint());
        model.put("metamac", applicationProperties.getMetamac());
        model.put("faviconUrl", this.faviconUrl);
        model.put("headerHtml", htmlService.getHeaderHtml());
        model.put("footerHtml", htmlService.getFooterHtml());
        
        UriBuilder urlBuilder = UriBuilder.fromUri(metadataProperties.getCaptchaUrl());
        urlBuilder.path("authentication.js");
        model.put("captchaAuthenticationUrl", urlBuilder.build().toString());

        Map<String, Object> flashMap = (Map<String, Object>) RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            model.putAll(flashMap);
        }
        return new ModelAndView("index", model);
    }
    
    @GetMapping("/external-login")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> externalLogin(@RequestParam String url, @CookieValue(value="authenticationtoken", required=false) String token) {
        String location = StringUtils.isNotBlank(token) ? UriBuilder.fromUri(url).queryParam("token", token).build().toString() : url;
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", location).build();
    }
    
    @GetMapping("/forced-external-login")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<Void> forceExternalLogin(@RequestParam String url, @CookieValue(value="authenticationtoken", required=false) String token) throws UnsupportedEncodingException {
        String loginUrl = metadataService.retrieveExternalUsersExternalWebApplicationUrlBase() + "/#/login?origin=" + URLEncoder.encode(url, "UTF-8");
        String location = StringUtils.isNotBlank(token) ? UriBuilder.fromUri(url).queryParam("token", token).build().toString() : loginUrl; 
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", location).build();
    }
}
