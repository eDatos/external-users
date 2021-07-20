package es.gobcan.istac.edatos.external.users.core.service;

public interface CaptchaService {
    void saveResponse(String key, Object response);
    Object getResponse(String key);
    boolean validateSimple(String userResponse, String sessionKey);
    boolean validateRecaptcha(String userResponse, String captchaAction);
    boolean validateCaptchaGobcan(String userResponse, String sessionKey);
}