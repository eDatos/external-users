package es.gobcan.istac.edatos.external.users.core.config;

public final class CaptchaConstants {
    
    // PROVIDERS
    public static final String CAPTCHA_PROVIDER_GOBCAN = "gobcan";
    public static final String CAPTCHA_PROVIDER_RECAPTCHA = "recaptcha";
    public static final String CAPTCHA_PROVIDER_SIMPLE = "simple";
    
    // METADATA FIELDS
    public static final String CAPTCHA_ENABLE = "metamac.portal.rest.external.authentication.captcha.enable";
    public static final String CAPTCHA_PROVIDER = "metamac.portal.rest.external.authentication.captcha.provider";
    
    // CONSTANTS FOR ALL PROVIDERS
    public static final String CAPTCHA_SESSION_ATTRIBUTE = "captcha_result";
    public static final String CAPTCHA_PICTURE_MODEL_ATTR = "captchaPictureUrl";
    public static final String CAPTCHA_SESSION_COOKIE = "captcha_validation_key";
    
    // SPECIFIC CONSTANTS FOR CAPTCHA SIMPLE 
    public static final int CAPTCHA_SIMPLE_WIDTH = 200;
    public static final int CAPTCHA_SIMPLE_HEIGHT = 50;
    
    // SPECIFIC CONSTANTS FOR CAPTCHA GOBCAN 
    public static final int CAPTCHA_GOBCAN_WIDTH = 300;
    public static final int CAPTCHA_GOBCAN_HEIGHT = 100;
    public static final String CAPTCHA_GOBCAN_WSDL_URL = "/wsdl/captcha-gobcan.wsdl";
    public static final String CAPTCHA_GOBCAN_IMAGE_FORMAT = "jpg";
    public static final String CAPTCHA_GOBCAN_FONTNAME = "Arial Narrow";
    public static final char[] CAPTCHA_GOBCAN_OPERANDS = "0123456789".toCharArray();
    public static final char[] CAPTCHA_GOBCAN_OPERATORS = "+-*".toCharArray();

    private CaptchaConstants() { }
}
