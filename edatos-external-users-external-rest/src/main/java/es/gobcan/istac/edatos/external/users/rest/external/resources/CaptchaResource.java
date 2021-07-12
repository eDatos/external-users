package es.gobcan.istac.edatos.external.users.rest.external.resources;

import static es.gobcan.istac.edatos.external.users.core.config.CaptchaConstants.CAPTCHA_GOBCAN_OPERANDS;
import static es.gobcan.istac.edatos.external.users.core.config.CaptchaConstants.CAPTCHA_GOBCAN_OPERATORS;

import es.gobcan.istac.edatos.external.users.core.config.CaptchaConstants;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;
import https.www_gobiernodecanarias_org.ws.wscaptcha.service_asmx.CaptchaService;
import https.www_gobiernodecanarias_org.ws.wscaptcha.service_asmx.CaptchaServiceSoap;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
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
    
    private final Random random = new Random();

    @Autowired
    private es.gobcan.istac.edatos.external.users.core.service.CaptchaService captchaService;

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

        request.getSession().setAttribute(CaptchaConstants.CAPTCHA_SESSION_ATTRIBUTE, captcha);
        Cookie cookie = new Cookie("captcha_validation_key", captchaService.saveSession(request.getSession()));
        cookie.setPath("/");
        response.addCookie(cookie);
        CaptchaServletUtil.writeImage(response, captcha.getImage());
    }
    
    @GetMapping("/picture/gobcan")
    public void drawGobcanCaptcha(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/" + CaptchaConstants.CAPTCHA_GOBCAN_IMAGE_FORMAT);

        try {
            int width = request.getParameter("width") != null ? Integer.parseInt(request.getParameter("width")) : CaptchaConstants. CAPTCHA_GOBCAN_WIDTH;
            int height = request.getParameter("height") != null ? Integer.parseInt(request.getParameter("height")) : CaptchaConstants.CAPTCHA_GOBCAN_HEIGHT;
            
            char operandChar1 = CAPTCHA_GOBCAN_OPERANDS[random.nextInt((CAPTCHA_GOBCAN_OPERANDS.length))];
            int operandInt1 = Integer.parseInt(String.valueOf(operandChar1));

            char operandChar2 = CAPTCHA_GOBCAN_OPERANDS[random.nextInt((CAPTCHA_GOBCAN_OPERANDS.length))];
            int operandInt2 = Integer.parseInt(String.valueOf(operandChar2));

            char operator = CAPTCHA_GOBCAN_OPERATORS[random.nextInt((CAPTCHA_GOBCAN_OPERATORS.length))];
            
            String keyword = "";
            int result = 0;
            if (operator == '+') {
                result = operandInt1 + operandInt2;
                keyword = operandChar1 + " m\u00e1s " + operandChar2;
            } else if (operator == '-') {
                if (operandInt1 < operandInt2) {
                    result = operandInt2 - operandInt1;
                    keyword = operandChar2 + " menos " + operandChar1;
                } else {
                    result = operandInt1 - operandInt2;
                    keyword = operandChar1 + " menos " + operandChar2;
                }
            } else if (operator == '*') {
                result = operandInt1 * operandInt2;
                keyword = operandChar1 + " por " + operandChar2;
            }

            URL wsdlLocation = getClass().getResource(CaptchaConstants.CAPTCHA_GOBCAN_WSDL_URL);
            CaptchaService service = new CaptchaService(wsdlLocation);
            CaptchaServiceSoap captchaServ = service.getCaptchaServiceSoap();

            byte[] imgCaptcha = captchaServ.captchaImage1(width, height, keyword, CaptchaConstants.CAPTCHA_GOBCAN_FONTNAME, 45f);
            CaptchaServletUtil.writeImage(response, ImageIO.read(new ByteArrayInputStream(imgCaptcha)));

            request.getSession().setAttribute(CaptchaConstants.CAPTCHA_SESSION_ATTRIBUTE, String.valueOf(result));
            
            Cookie cookie = new Cookie(CaptchaConstants.CAPTCHA_SESSION_COOKIE, captchaService.saveSession(request.getSession()));
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            throw new EDatosException(ServiceExceptionType.GENERIC_ERROR);
        }
    }

    @GetMapping(value="/authentication.js")
    public String authentication(HttpServletRequest request, Model model) {

        String captchaProvider = metadataService.retrieveCaptchaProvider();
        if (CaptchaConstants.CAPTCHA_PROVIDER_SIMPLE.equals(captchaProvider)) {
            String captchaPictureUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(BASE_URL + "/picture/simple").build().toUriString();
            model.addAttribute(CaptchaConstants.CAPTCHA_PICTURE_MODEL_ATTR, captchaPictureUrl);
            return "authentication-simple";
        } else if(CaptchaConstants.CAPTCHA_PROVIDER_GOBCAN.equals(captchaProvider)) {
            String captchaPictureUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(BASE_URL + "/picture/gobcan").build().toUriString();
            model.addAttribute(CaptchaConstants.CAPTCHA_PICTURE_MODEL_ATTR, captchaPictureUrl);
            return "authentication-gobcan";
        }
        return null;
    }
}
