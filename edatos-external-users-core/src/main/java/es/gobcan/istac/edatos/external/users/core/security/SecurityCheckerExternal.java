package es.gobcan.istac.edatos.external.users.core.security;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ExternalUserRole;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Role;

// TODO EDATOS-3106 Revisar esta clase. ¿Son necesarios todos estos métodos? Cambiar el nombre de la clase.
@Component("secCheckerExternal")
public class SecurityCheckerExternal {

    private static final String ACL_APP_NAME = "GESTOR_OPERACIONES";
    private static final String SEPARATOR = "#";

    public boolean canAccessCategory(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    public boolean canAccessFilters(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    public boolean canUpdateFilters(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    public boolean canDeleteFilters(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    public boolean canUpdateCategoryTree(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    public boolean canAccessFavorites(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    public boolean canCreateFavorites(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    public boolean canUpdateFavorites(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    public boolean canDeleteFavorites(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    public boolean canAccessUser(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    public boolean canModifyUserStatus(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    public boolean canCreateUser(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    public boolean canUpdateUser(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    public boolean canDeleteUser(Authentication authentication) {
        return this.isExternalUser(authentication);
    }

    private boolean isExternalUser(Authentication authentication) {
        return this.hasRole(authentication, ExternalUserRole.USER);
    }

    private boolean isAnyUser(Authentication authentication) {
        return this.hasRole(authentication, ExternalUserRole.ANY_ROLE_ALLOWED);
    }

    private boolean hasRole(Authentication authentication, ExternalUserRole userRole) {
        return authentication.getAuthorities().stream().anyMatch(authority -> {
            String role = authority.getAuthority();
            return role.equals(userRole.name());
        });
    }
}
