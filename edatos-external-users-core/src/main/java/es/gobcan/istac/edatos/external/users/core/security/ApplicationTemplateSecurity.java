package es.gobcan.istac.edatos.external.users.core.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Role;

// TODO EDATOS-3106 Revisar esta clase. ¿Son necesarios todos estos métodos? Cambiar el nombre de la clase.
@Component("secChecker")
public class ApplicationTemplateSecurity {

    // TODO(EDATOS-3141,EDATOS-3276): Add edatos-external-users-internal app name to access-control,
    //  the one here is borrowed from another app.
    private static final String ACL_APP_NAME = "GESTOR_OPERACIONES";
    private static final String SEPARATOR = "#";

    public boolean canAccessCategory(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean canAccessFilters(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean canCreateFilters(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean canUpdateFilters(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean canDeleteFilters(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean canAccessFavorites(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean canCreateFavorites(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean canUpdateFavorites(Authentication authentication) {
        return this.esAdmin(authentication);
    }

    public boolean canDeleteFavorites(Authentication authentication) {
        return this.esAdmin(authentication);
    }

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

    private boolean esAdmin(Authentication authentication) {
        return this.hasRole(authentication, Role.ADMINISTRADOR);
    }

    private boolean esUsuario(Authentication authentication) {
        return this.hasRole(authentication, Role.ANY_ROLE_ALLOWED);
    }

    private boolean hasRole(Authentication authentication, Role userRole) {
        // TODO(EDATOS-3276): this is a rudimentary way to implement role cheking.
        //  In the future, sso-client or derivates should be used.
        return authentication.getAuthorities().stream().anyMatch(authority -> {
            String[] appRole = authority.getAuthority().split(SEPARATOR);
            String application = appRole[0];
            String role = appRole[1];
            return application.equals(ACL_APP_NAME) && role.equals(userRole.name());
        });
    }
}
