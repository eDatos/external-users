package es.gobcan.istac.edatos.external.users.core.service.impl;

import org.siemac.edatos.core.common.conf.ConfigurationServiceImpl;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.config.CaptchaConstants;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

@Service
public class MetadataConfigurationServiceImpl extends ConfigurationServiceImpl implements MetadataConfigurationService {
    
    @Override
    public boolean retrieveCaptchaEnable() throws EDatosException {
        return retrievePropertyBoolean(CaptchaConstants.CAPTCHA_ENABLE);
    }

    @Override
    public String retrieveCaptchaProvider() throws EDatosException {
        return retrieveProperty(CaptchaConstants.CAPTCHA_PROVIDER);
    }
}