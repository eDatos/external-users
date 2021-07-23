package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.UriBuilder;

import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.gobcan.istac.edatos.external.users.core.config.CaptchaConstants;
import es.gobcan.istac.edatos.external.users.core.config.MetadataProperties;
import es.gobcan.istac.edatos.external.users.core.errors.CaptchaClientError;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.core.service.CaptchaService;
import es.gobcan.istac.edatos.external.users.core.util.RecaptchaVerification;
import jersey.repackaged.com.google.common.cache.Cache;
import jersey.repackaged.com.google.common.cache.CacheBuilder;
import nl.captcha.Captcha;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private Cache<Object, Object> responses = CacheBuilder.newBuilder()
            .concurrencyLevel(10)
            .expireAfterWrite(CaptchaConstants.CAPTCHA_LIFESPAN, TimeUnit.MILLISECONDS)
            .build();
    private RestTemplate restTemplate = new RestTemplate();
    
    @Autowired
    private MetadataProperties metadataProperties;

    @Override
    public void saveResponse(String key, Object session) {
        responses.put(key, session);
    }

    @Override
    public Object getResponse(String key) {
        Object response = responses.getIfPresent(key);
        responses.invalidate(key);
        return response;
    }

    @Override
    public boolean validateSimple(String userResponse, String sessionKey) {
        Captcha simpleCaptcha = (Captcha) getResponse(sessionKey);
        return userResponse != null && simpleCaptcha != null && simpleCaptcha.isCorrect(userResponse);
    }

    @Override
    public boolean validateRecaptcha(String userResponse, String captchaAction) {
        UriBuilder urlBuilder = UriBuilder.fromUri(CaptchaConstants.RECAPTCHA_VERIFICATION_API);
        urlBuilder.queryParam("secret", metadataProperties.getRecaptchaSecretKey());
        urlBuilder.queryParam("response", userResponse);

        ResponseEntity<RecaptchaVerification> recaptchaVerificationResponse = restTemplate.getForEntity(urlBuilder.build(), RecaptchaVerification.class);
        RecaptchaVerification recaptchaVerification = recaptchaVerificationResponse.getBody();

        if (!recaptchaVerification.wasASuccess()) {
            if (recaptchaVerification.hasClientError()) {
                throw new CaptchaClientError();
            } else {
                throw new EDatosException(ServiceExceptionType.GENERIC_ERROR);
            }
        } else if (!recaptchaVerification.getAction().equals(captchaAction)) {
            throw new EDatosException(ServiceExceptionType.GENERIC_ERROR);
        }

        return recaptchaVerification.getScore() >= CaptchaConstants.RECAPTCHA_SCORE_THRESHOLD;
    }

    @Override
    public boolean validateCaptchaGobcan(String userResponse, String sessionKey) {
        String gobcanCaptchaResponse = (String) getResponse(sessionKey);
        return userResponse != null && gobcanCaptchaResponse != null && gobcanCaptchaResponse.equals(userResponse);
    }
}
