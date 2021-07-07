package es.gobcan.istac.edatos.external.users.core.config;

public final class CaptchaConstants {
    
    public static final String CAPTCHA_PROVIDER_GOBCAN = "gobcan";
    public static final String CAPTCHA_PROVIDER_RECAPTCHA = "recaptcha";
    public static final String CAPTCHA_PROVIDER_SIMPLE = "simple";
    
    public static final String CAPTCHA_ENABLE = "metamac.portal.rest.external.authentication.captcha.enable";
    public static final String CAPTCHA_PROVIDER = "metamac.portal.rest.external.authentication.captcha.provider";
    
    public static final int CAPTCHA_SIMPLE_WIDTH = 200;
    public static final int CAPTCHA_SIMPLE_HEIGHT = 50;

    private CaptchaConstants() { }
}
