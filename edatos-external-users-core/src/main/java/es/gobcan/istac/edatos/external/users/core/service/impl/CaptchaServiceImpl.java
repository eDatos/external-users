package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.UriBuilder;

import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.gobcan.istac.edatos.external.users.core.config.CaptchaConstants;
import es.gobcan.istac.edatos.external.users.core.errors.CaptchaClientError;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.core.service.CaptchaService;
import es.gobcan.istac.edatos.external.users.core.util.RecaptchaVerification;
import nl.captcha.Captcha;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private Map<String, HttpSession> sessions = new HashMap<>();
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void saveSession(String key, HttpSession session) {
        sessions.put(key, session);
    }

    @Override
    public HttpSession getSession(String key) {
        return (key == null) ? null : sessions.remove(key);
    }

    @Override
    public boolean validateSimple(String response, HttpSession session) {
        if(session == null) {
            return false;
        }
        Captcha simpleCaptcha = (Captcha) session.getAttribute(CaptchaConstants.CAPTCHA_SESSION_ATTRIBUTE);
        return response != null && simpleCaptcha != null && simpleCaptcha.isCorrect(response);
    }

    @Override
    public boolean validateRecaptcha(String response) {
        UriBuilder urlBuilder = UriBuilder.fromUri(CaptchaConstants.RECAPTCHA_VERIFICATION_API);
        urlBuilder.queryParam("secret", "6LfVgBAbAAAAAB9YjsovVuStNs7oEigoBeJnSB9x");
        urlBuilder.queryParam("response", response);

        ResponseEntity<RecaptchaVerification> recaptchaVerificationResponse = restTemplate.getForEntity(urlBuilder.build(), RecaptchaVerification.class);
        RecaptchaVerification recaptchaVerification = recaptchaVerificationResponse.getBody();
        
        if(!recaptchaVerification.wasASuccess()) {
            if(recaptchaVerification.hasClientError()) {
                throw new CaptchaClientError();
            } else {
                throw new EDatosException(ServiceExceptionType.GENERIC_ERROR);
            }
        }
        
        return recaptchaVerification.getScore() >= 0.5;
    }

    @Override
    public boolean validateCaptchaGobcan(String response, HttpSession session) {
        if(session == null) {
            return false;
        }
        String gobcanCaptchaAnswer = (String) session.getAttribute(CaptchaConstants.CAPTCHA_SESSION_ATTRIBUTE);
        return response != null && gobcanCaptchaAnswer != null && gobcanCaptchaAnswer.equals(response);
    }
}
