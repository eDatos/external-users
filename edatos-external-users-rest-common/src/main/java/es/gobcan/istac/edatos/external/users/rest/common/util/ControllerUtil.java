package es.gobcan.istac.edatos.external.users.rest.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import es.gobcan.istac.edatos.external.users.core.domain.FicheroEntity;
import es.gobcan.istac.edatos.external.users.core.errors.CustomParameterizedExceptionBuilder;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;

public final class ControllerUtil {

    private static final Logger log = LoggerFactory.getLogger(ControllerUtil.class);

    private ControllerUtil() {
        super();
    }

    public static void download(FicheroEntity fichero, HttpServletResponse response) {
        try (InputStream is = fichero.getDatos().getBinaryStream()) {
            copyContentToResponse(is, fichero.getNombre(), fichero.getTipoContenido(), fichero.getLongitud(), response);
        } catch (IOException | SQLException e) {
            log.error("Exception obtaining the file {}", fichero.getId(), e);
            throw new CustomParameterizedExceptionBuilder().message(String.format("Exception obtaining the file %s", fichero.getId())).code(ErrorConstants.FICHERO_NO_ENCONTRADO).build();
        }
    }

    private static void copyContentToResponse(InputStream inputStream, String name, String contentType, Long length, HttpServletResponse response) throws IOException {
        if (length != null) {
            response.setContentLength(length.intValue());
        }
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", name));
        try (OutputStream os = response.getOutputStream()) {
            StreamUtils.copy(inputStream, os);
        }
    }
}
