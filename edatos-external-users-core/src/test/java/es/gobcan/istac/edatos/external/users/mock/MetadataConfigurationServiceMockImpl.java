package es.gobcan.istac.edatos.external.users.mock;

import org.siemac.edatos.common.test.mock.ConfigurationServiceMockImpl;
import org.siemac.edatos.core.common.exception.EDatosException;

import es.gobcan.istac.edatos.external.users.core.config.ExternalUsersConfigurationConstants;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

public class MetadataConfigurationServiceMockImpl extends ConfigurationServiceMockImpl implements MetadataConfigurationService {

    @Override
    public boolean retrieveCaptchaEnable() throws EDatosException {
        return retrievePropertyBoolean(ExternalUsersConfigurationConstants.CAPTCHA_ENABLE);
    }

    @Override
    public String retrieveCaptchaProvider() throws EDatosException {
        return retrieveProperty(ExternalUsersConfigurationConstants.CAPTCHA_PROVIDER);
    }

    @Override
    public String retrieveRecaptchaSiteKey() throws EDatosException {
        return retrieveProperty(ExternalUsersConfigurationConstants.CAPTCHA_RECAPTCHA_SITE_KEY);
    }

    @Override
    public String retrieveRecaptchaSecretKey() throws EDatosException {
        return retrieveProperty(ExternalUsersConfigurationConstants.CAPTCHA_RECAPTCHA_SECRET_KEY);
    }

    @Override
    public String retrieveResetPassowrdUrl() throws EDatosException {
        return retrieveProperty(ExternalUsersConfigurationConstants.RESET_PASSWORD_URL);
    }

    @Override
    public String retrieveCronExpressionSendNoticeJob() throws EDatosException {
        return retrieveProperty(ExternalUsersConfigurationConstants.CRON_EXPRESSION_SEND_NOTICE_JOB);
    }
}
