package es.gobcan.istac.edatos.external.users.core.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

@Component("metadataProperties")
public class MetadataProperties {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MetadataConfigurationService configurationService;

    private String metamacNavbar;
    private String metamacCasPrefix;
    private String metamacCasLoginUrl;
    private String metamacCasLogoutUrl;
    private String defaultLanguage;

    @PostConstruct
    public void setValues() {
        try {
            metamacNavbar = normalizeUrl(configurationService.retrieveNavbarUrl());
            metamacCasPrefix = normalizeUrl(configurationService.retrieveSecurityCasServerUrlPrefix());
            metamacCasLoginUrl = normalizeUrl(configurationService.retrieveSecurityCasServiceLoginUrl());
            metamacCasLogoutUrl = normalizeUrl(configurationService.retrieveSecurityCasServiceLogoutUrl());
            List<String> languages = configurationService.retrieveLanguages();
            defaultLanguage = languages.get(0);
        } catch (Exception e) {
            log.error("Error getting the value of a metadata {}", e);
        }
    }

    public String getMetamacNavbar() {
        return metamacNavbar;
    }

    public String getMetamacCasPrefix() {
        return metamacCasPrefix;
    }

    public String getMetamacCasLoginUrl() {
        return metamacCasLoginUrl;
    }

    public String getMetamacCasLogoutUrl() {
        return metamacCasLogoutUrl;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    // 
    public List<String> getLanguages() {
        // The languages are retrieved each time it is needed so that multi-language fields can be updated
        return configurationService.retrieveLanguages();
    }

    private String normalizeUrl(String url) {
        return StringUtils.removeEnd(url, "/");
    }
}
