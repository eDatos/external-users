package es.gobcan.istac.edatos.external.users.core.security;

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
        return this.isAdmin(authentication);
    }

    public boolean canAccessFilters(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canCreateFilters(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canUpdateFilters(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canDeleteFilters(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canAccessFavorites(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canCreateFavorites(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canUpdateFavorites(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canDeleteFavorites(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canAccessAudit(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canAccessLogs(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canUpdateLogs(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canAccessUser(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canModifyUserStatus(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canCreateUser(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canUpdateUser(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canDeleteUser(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canAccessDocument(Authentication authentication) {
        return this.isUser(authentication);
    }

    public boolean canCreateDocument(Authentication authentication) {
        return this.isUser(authentication);
    }

    public boolean canUpdateDocument(Authentication authentication) {
        return this.isUser(authentication);
    }

    public boolean canDeleteDocument(Authentication authentication) {
        return this.isUser(authentication);
    }

    public boolean canAccessMetrics(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canAccessHealth(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    public boolean canAccessConfig(Authentication authentication) {
        return this.isAdmin(authentication);
    }

    private boolean isAdmin(Authentication authentication) {
        return this.hasRole(authentication, Role.ADMINISTRADOR);
    }

    private boolean isUser(Authentication authentication) {
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
