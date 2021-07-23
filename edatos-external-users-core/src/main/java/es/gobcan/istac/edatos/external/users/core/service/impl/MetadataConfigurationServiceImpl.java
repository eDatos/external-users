package es.gobcan.istac.edatos.external.users.core.service.impl;

import org.siemac.edatos.core.common.conf.ConfigurationServiceImpl;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.config.MetadataConstants;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

@Service
public class MetadataConfigurationServiceImpl extends ConfigurationServiceImpl implements MetadataConfigurationService {

    @Override
    public boolean retrieveCaptchaEnable() throws EDatosException {
        return retrievePropertyBoolean(MetadataConstants.CAPTCHA_ENABLE);
    }

    @Override
    public String retrieveCaptchaProvider() throws EDatosException {
        return retrieveProperty(MetadataConstants.CAPTCHA_PROVIDER);
    }
    
    @Override
    public String retrieveRecaptchaSiteKey() throws EDatosException {
        return retrieveProperty(MetadataConstants.CAPTCHA_RECAPTCHA_SITE_KEY);
    }

    @Override
    public String retrieveRecaptchaSecretKey() throws EDatosException {
        return retrieveProperty(MetadataConstants.CAPTCHA_RECAPTCHA_SECRET_KEY);
    }
}