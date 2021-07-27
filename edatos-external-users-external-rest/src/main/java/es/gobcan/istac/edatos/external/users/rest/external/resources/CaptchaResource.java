package es.gobcan.istac.edatos.external.users.rest.external.resources;

import static es.gobcan.istac.edatos.external.users.core.config.CaptchaConstants.CAPTCHA_GOBCAN_OPERANDS;
import static es.gobcan.istac.edatos.external.users.core.config.CaptchaConstants.CAPTCHA_GOBCAN_OPERATORS;

import es.gobcan.istac.edatos.external.users.core.config.CaptchaConstants;
import es.gobcan.istac.edatos.external.users.core.config.MetadataProperties;
import es.gobcan.istac.edatos.external.users.core.errors.CaptchaClientError;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;

import https.www_gobiernodecanarias_org.ws.wscaptcha.service_asmx.CaptchaService;
import https.www_gobiernodecanarias_org.ws.wscaptcha.service_asmx.CaptchaServiceSoap;
import liquibase.util.BooleanUtils;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private MetadataProperties metadataService;

    @GetMapping("/validate")
    @ResponseBody
    public ResponseEntity<Boolean> validateCaptcha(@RequestParam(required = false) String userValue, @RequestParam(required = false) String sessionKey,
            @RequestParam(required = false) String captchaAction) {
        
        boolean captchaEnabled = metadataService.isCaptchaEnable();
        String captchaProvider = metadataService.getCaptchaProvider();
        if (BooleanUtils.equals(null, captchaEnabled)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else if(!captchaEnabled) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else if(userValue == null) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        boolean valid = false;
        if (CaptchaConstants.CAPTCHA_PROVIDER_GOBCAN.equals(captchaProvider)) {
            valid = captchaService.validateCaptchaGobcan(userValue, sessionKey);
        } else if (CaptchaConstants.CAPTCHA_PROVIDER_RECAPTCHA.equals(captchaProvider)) {
            try {
                valid = captchaService.validateRecaptcha(userValue, captchaAction);
            } catch (CaptchaClientError e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (EDatosException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if (CaptchaConstants.CAPTCHA_PROVIDER_SIMPLE.equals(captchaProvider)) {
            valid = captchaService.validateSimple(userValue, sessionKey);
        }

        return new ResponseEntity<>(valid, HttpStatus.OK);
    }

    @GetMapping("/picture/simple")
    public void drawSimpleCaptcha(HttpServletRequest request, HttpServletResponse response, @RequestParam String sessionKey,
            @RequestParam(required = false, defaultValue = "" + CaptchaConstants.CAPTCHA_SIMPLE_WIDTH) int width,
            @RequestParam(required = false, defaultValue = "" + CaptchaConstants.CAPTCHA_SIMPLE_HEIGHT) int height) {
        
        Captcha captcha = new Captcha.Builder(width, height).addText().addBackground().addNoise().gimp().addBorder().build();

        captchaService.saveResponse(sessionKey, captcha);
        CaptchaServletUtil.writeImage(response, captcha.getImage());
    }

    @GetMapping("/picture/gobcan")
    public void drawGobcanCaptcha(HttpServletRequest request, HttpServletResponse response, @RequestParam String sessionKey,
            @RequestParam(required = false, defaultValue = "" + CaptchaConstants.CAPTCHA_GOBCAN_WIDTH) int width,
            @RequestParam(required = false, defaultValue = "" + CaptchaConstants.CAPTCHA_GOBCAN_HEIGHT) int height) {
        
        response.setContentType("image/" + CaptchaConstants.CAPTCHA_GOBCAN_IMAGE_FORMAT);

        try {
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

            URL wsdlLocation = getClass().getResource(CaptchaConstants.CAPTCHA_GOBCAN_WSDL_PATH);
            CaptchaService service = new CaptchaService(wsdlLocation);
            CaptchaServiceSoap captchaServ = service.getCaptchaServiceSoap();

            byte[] imgCaptcha = captchaServ.captchaImage1(width, height, keyword, CaptchaConstants.CAPTCHA_GOBCAN_FONTNAME, 45f);
            CaptchaServletUtil.writeImage(response, ImageIO.read(new ByteArrayInputStream(imgCaptcha)));

            captchaService.saveResponse(sessionKey, String.valueOf(result));            
        } catch (Exception e) {
            throw new EDatosException(ServiceExceptionType.GENERIC_ERROR);
        }
    }

    @GetMapping(value="/authentication.js")
    public String authentication(HttpServletRequest request, Model model) {

        String captchaProvider = metadataService.getCaptchaProvider();
        boolean captchaEnable = metadataService.isCaptchaEnable();
        model.addAttribute(CaptchaConstants.CAPTCHA_ENABLED_MODEL_ATTR, captchaEnable);
        if (CaptchaConstants.CAPTCHA_PROVIDER_SIMPLE.equals(captchaProvider)) {
            String captchaPictureUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(BASE_URL + "/picture/simple").build().toUriString();
            model.addAttribute(CaptchaConstants.CAPTCHA_PICTURE_MODEL_ATTR, captchaPictureUrl);
            return "authentication-simple";
        } else if(CaptchaConstants.CAPTCHA_PROVIDER_GOBCAN.equals(captchaProvider)) {
            String captchaPictureUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(BASE_URL + "/picture/gobcan").build().toUriString();
            model.addAttribute(CaptchaConstants.CAPTCHA_PICTURE_MODEL_ATTR, captchaPictureUrl);
            return "authentication-gobcan";
        } else if(CaptchaConstants.CAPTCHA_PROVIDER_RECAPTCHA.equals(captchaProvider)) {
            String recaptchaSiteKey = metadataService.getRecaptchaSiteKey();
            model.addAttribute(CaptchaConstants.RECAPTCHA_SITE_KEY_MODEL_ATTR, recaptchaSiteKey);
            return "authentication-recaptcha";
        }
        return null;
    }
}
