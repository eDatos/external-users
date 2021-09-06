package es.gobcan.istac.edatos.external.users.web.util;

import es.gobcan.istac.edatos.external.users.web.config.ApplicationProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import java.util.Arrays;

public class SecurityCookiesUtil {

    public static final String SERVICE_TICKET_COOKIE = "service_ticket";
    public static final String ROOT_PATH = "/";

    public static String getCookiePath(ApplicationProperties applicationProperties) {
        if (StringUtils.isBlank(applicationProperties.getEndpoint().getAppUrl())) {
            return ROOT_PATH;
        }

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(applicationProperties.getEndpoint().getAppUrl()).build();
        String path = uriComponents.getPath();

        if (StringUtils.isBlank(path)) {
            return ROOT_PATH;
        }

        return StringUtils.prependIfMissing(path, ROOT_PATH);
    }

    public static String getServiceTicketCookie(Cookie[] cookies) {
        return Arrays.stream(cookies)
            .filter(c -> SERVICE_TICKET_COOKIE.equals(c.getName()))
            .map(Cookie::getValue)
            .findAny().orElse(null);
    }
}
