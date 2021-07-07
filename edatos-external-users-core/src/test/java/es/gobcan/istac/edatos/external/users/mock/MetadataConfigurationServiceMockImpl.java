package es.gobcan.istac.edatos.external.users.mock;

import org.siemac.edatos.common.test.mock.ConfigurationServiceMockImpl;
import org.siemac.edatos.core.common.exception.EDatosException;

import es.gobcan.istac.edatos.external.users.core.config.CaptchaConstants;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

public class MetadataConfigurationServiceMockImpl extends ConfigurationServiceMockImpl implements MetadataConfigurationService {

    @Override
    public boolean retrieveCaptchaEnable() throws EDatosException {
        return retrievePropertyBoolean(CaptchaConstants.CAPTCHA_ENABLE);
    }

    @Override
    public String retrieveCaptchaProvider() throws EDatosException {
        return retrieveProperty(CaptchaConstants.CAPTCHA_PROVIDER);
    }
}
