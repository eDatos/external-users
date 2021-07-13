package es.gobcan.istac.edatos.external.users.core.service;

import javax.servlet.http.HttpSession;

public interface CaptchaService {
    String saveSession(HttpSession session);
    HttpSession getSession(String key);
    boolean validateSimple(String response, HttpSession session);
    boolean validateRecaptcha(String response);
    boolean validateCaptchaGobcan(String response, HttpSession session);
}