package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";
    private static final String ERROR_FRAGMENT = "/#/notfound";

    @RequestMapping(path = PATH)
    public void handleError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return;
            }
        }

        String errorUrl = request.getContextPath() + ERROR_FRAGMENT;
        response.sendRedirect(errorUrl);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}
