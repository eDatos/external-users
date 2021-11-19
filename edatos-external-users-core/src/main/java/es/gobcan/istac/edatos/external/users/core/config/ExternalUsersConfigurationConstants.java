package es.gobcan.istac.edatos.external.users.core.config;

import org.siemac.edatos.core.common.constants.shared.ConfigurationConstants;

public final class ExternalUsersConfigurationConstants extends ConfigurationConstants {

    public static final String CAPTCHA_ENABLE = "metamac.portal.rest.external.authentication.captcha.enable";
    public static final String CAPTCHA_PROVIDER = "metamac.portal.rest.external.authentication.captcha.provider";
    public static final String CAPTCHA_RECAPTCHA_SITE_KEY = "metamac.portal.rest.external.authentication.captcha.public_key";
    public static final String CAPTCHA_RECAPTCHA_SECRET_KEY = "metamac.portal.rest.external.authentication.captcha.private_key";

    public static final String BASE_URL = "metamac.edatos_external_users.rest.external.base";

    public static final String CRON_EXPRESSION_SEND_NOTICE_JOB = "metamac.edatos_external_users.rest.internal.job.send_notice.cron_expression";

    public static final String VISUALIZER_PATH = "metamac.edatos_external_users.web.visualizer_path";

    private ExternalUsersConfigurationConstants() {

    }
}
