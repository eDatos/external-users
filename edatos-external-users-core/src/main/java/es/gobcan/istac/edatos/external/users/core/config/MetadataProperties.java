package es.gobcan.istac.edatos.external.users.core.config;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.service.HtmlService;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

@Component("metadataProperties")
public class MetadataProperties {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MetadataConfigurationService configurationService;

    @Autowired
    private HtmlService htmlService;

    private String metamacNavbar;
    private String metamacCasPrefix;
    private String metamacCasLoginUrl;
    private String metamacCasLogoutUrl;
    private String defaultLanguage;
    private List<String> availableLanguages;

    private boolean captchaEnable;
    private String captchaProvider;
    private String recaptchaSiteKey;
    private String recaptchaSecretKey;
    private String captchaUrl;

    @PostConstruct
    public void setValues() {
        try {
            metamacNavbar = normalizeUrl(configurationService.retrieveNavbarUrl());
            metamacCasPrefix = normalizeUrl(configurationService.retrieveSecurityCasServerUrlPrefix());
            metamacCasLoginUrl = normalizeUrl(configurationService.retrieveSecurityCasServiceLoginUrl());
            metamacCasLogoutUrl = normalizeUrl(configurationService.retrieveSecurityCasServiceLogoutUrl());
            captchaEnable = configurationService.retrieveCaptchaEnable();
            captchaProvider = configurationService.retrieveCaptchaProvider();
            recaptchaSiteKey = configurationService.retrieveRecaptchaSiteKey();
            recaptchaSecretKey = configurationService.retrieveRecaptchaSecretKey();
            captchaUrl = normalizeUrl(configurationService.retrieveCaptchaExternalApiUrlBase());
            availableLanguages = configurationService.retrieveLanguages();
            defaultLanguage = availableLanguages.get(0);
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

    public boolean isCaptchaEnable() {
        return captchaEnable;
    }

    public String getCaptchaProvider() {
        return captchaProvider;
    }

    public String getRecaptchaSiteKey() {
        return recaptchaSiteKey;
    }

    public String getRecaptchaSecretKey() {
        return recaptchaSecretKey;
    }

    public String getCaptchaUrl() {
        return captchaUrl;
    }

    public List<String> getAvailableLanguages() {
        // The languages are retrieved each time it is needed so that multi-language fields can be updated
        return availableLanguages;
    }

    private String normalizeUrl(String url) {
        return StringUtils.removeEnd(url, "/");
    }
}
