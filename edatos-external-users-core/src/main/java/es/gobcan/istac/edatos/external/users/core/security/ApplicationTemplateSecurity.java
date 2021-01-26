
package es.gobcan.istac.edatos.external.users.core.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Rol;

// TODO EDATOS-3106 Revisar esta clase. ¿Son necesarios todos estos métodos? Cambiar el nombre de la clase.
@Component("secChecker")
public class ApplicationTemplateSecurity {

    // TODO EDATOS-3141 Cambiar nombre de aplicación
    private final static String ACL_APP_NAME = "GESTOR_OPERACIONES";
    private final static String SEPARATOR = "#";

    public boolean puedeConsultarAuditoria(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean puedeConsultarLogs(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean puedeModificarLogs(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean puedeConsultarUsuario(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean puedeConsultarUsuarioLdap(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean puedeCrearUsuario(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean puedeModificarUsuario(Authentication authentication, String login) {
        String userLogin = SecurityUtils.getCurrentUserLogin();
        return this.esAdmin(authentication) || (StringUtils.isNotBlank(userLogin) && StringUtils.isNotBlank(login) && userLogin.equals(login));
    }

    public boolean puedeBorrarUsuario(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean puedeConsultarDocumento(Authentication authentication) {
        return this.esUsuario(authentication);
    }

    public boolean puedeCrearDocumento(Authentication authentication) {
        return this.esUsuario(authentication);
    }

    public boolean puedeModificarDocumento(Authentication authentication) {
        return this.esUsuario(authentication);
    }

    public boolean puedeBorrarDocumento(Authentication authentication) {
        return this.esUsuario(authentication);
    }

    public boolean puedeConsultarMetrica(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean puedeConsultarSalud(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean puedeConsultarConfig(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean puedeConsultarApi(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    private boolean esAdmin(Authentication authentication) {
        return this.hasRole(authentication, Rol.ADMINISTRADOR);
    }

    private boolean esUsuario(Authentication authentication) {
        return this.hasRole(authentication, Rol.USER);
    }

    private boolean hasRole(Authentication authentication, Rol userRole) {
        return authentication.getAuthorities().stream().anyMatch(authority -> {
            String[] appRole = authority.getAuthority().split(SEPARATOR);
            String application = appRole[0];
            String role = appRole[1];
            return application.equals(ACL_APP_NAME) && role.equals(userRole.name());
        });
    }
}