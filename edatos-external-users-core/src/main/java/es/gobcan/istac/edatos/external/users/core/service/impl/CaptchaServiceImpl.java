package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.service.CaptchaService;
import nl.captcha.Captcha;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private Map<String, HttpSession> sessions = new HashMap<>();

    @Override
    public String saveSession(HttpSession session) {
        if(session == null) {
            return null;
        } else {
            String key = UUID.randomUUID().toString();
            sessions.put(key, session);
            return key;
        }
    }

    @Override
    public HttpSession getSession(String key) {
        return (key == null) ? null : sessions.remove(key);
    }

    @Override
    public boolean validateSimple(String response, HttpSession session) {
        Captcha simpleCaptcha = (Captcha) session.getAttribute(Captcha.NAME);
        return response != null && simpleCaptcha != null && simpleCaptcha.isCorrect(response);
    }

    @Override
    public boolean validateRecaptchaGobcan(String response, HttpSession session) {
        // TODO
        return true;
    }

    @Override
    public boolean validateCaptchaGobcan(String response, HttpSession session) {
        // TODO
        return true;
    }
}
