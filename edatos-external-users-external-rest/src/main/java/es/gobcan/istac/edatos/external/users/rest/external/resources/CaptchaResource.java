package es.gobcan.istac.edatos.external.users.rest.external.resources;

import es.gobcan.istac.edatos.external.users.core.config.CaptchaConstants;
import es.gobcan.istac.edatos.external.users.core.service.CaptchaService;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import nl.captcha.Captcha;
import nl.captcha.servlet.CaptchaServletUtil;

@Controller
@RequestMapping(CaptchaResource.BASE_URL)
public class CaptchaResource extends AbstractResource {

    public static final String BASE_URL = "/api/captcha";

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private MetadataConfigurationService metadataService;

    @GetMapping("/validate")
    @ResponseBody
    public ResponseEntity<Boolean> validateCaptcha(@RequestParam(required=false) String userValue, @RequestParam(required=false) String sessionKey) {
        boolean captchaEnabled;
        String captchaProvider;
        
        if(userValue == null || sessionKey == null) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        try {
            captchaEnabled = metadataService.retrieveCaptchaEnable();
        } catch (EDatosException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (captchaEnabled) {
            try {
                captchaProvider = metadataService.retrieveCaptchaProvider();
            } catch (EDatosException e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        boolean valid = false;
        HttpSession session = captchaService.getSession(sessionKey);
        if (session == null) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        if (CaptchaConstants.CAPTCHA_PROVIDER_GOBCAN.equals(captchaProvider)) {
            valid = captchaService.validateCaptchaGobcan(userValue, session);
        } else if (CaptchaConstants.CAPTCHA_PROVIDER_RECAPTCHA.equals(captchaProvider)) {
            try {
                valid = captchaService.validateRecaptchaGobcan(userValue, session);
            } catch (EDatosException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if (CaptchaConstants.CAPTCHA_PROVIDER_SIMPLE.equals(captchaProvider)) {
            valid = captchaService.validateSimple(userValue, session);
        }

        return new ResponseEntity<>(valid, HttpStatus.OK);
    }

    @GetMapping("/picture/simple")
    public void drawSimpleCaptcha(HttpServletRequest request, HttpServletResponse response) {
        Captcha captcha = new Captcha.Builder(CaptchaConstants.CAPTCHA_SIMPLE_WIDTH, CaptchaConstants.CAPTCHA_SIMPLE_HEIGHT).addText().addBackground().addNoise().gimp().addBorder().build();

        request.getSession().setAttribute(Captcha.NAME, captcha);
        Cookie cookie = new Cookie("captcha_validation_key", captchaService.saveSession(request.getSession()));
        cookie.setPath("/");
        response.addCookie(cookie);
        CaptchaServletUtil.writeImage(response, captcha.getImage());
    }

    @GetMapping(value="/authentication.js")
    public String authentication(HttpServletRequest request, Model model) {

        String captchaProvider = metadataService.retrieveCaptchaProvider();
        if (CaptchaConstants.CAPTCHA_PROVIDER_SIMPLE.equals(captchaProvider)) {
            String captchaPictureUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(BASE_URL + "/picture/simple").build().toUriString();
            model.addAttribute("baseUrl", captchaPictureUrl);
            return "authentication-simple";
        }
        return null;
    }
}
