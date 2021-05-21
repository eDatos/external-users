import { Injectable } from '@angular/core';
import { Role } from '@app/core/model';
import { Principal } from './principal.service';

export const USER_MANAGEMENT_ROLES = [Role.ADMINISTRADOR];
export const HERRAMIENTAS_ROLES = [Role.ADMINISTRADOR];
export const ADMINISTRACION_ROLES = [Role.ADMINISTRADOR];

export const FILTER_ROLES = [Role.ADMINISTRADOR];
export const FAVORITE_ROLES = [Role.ADMINISTRADOR];
export const EXTERNAL_USER_ROLES = [Role.ADMINISTRADOR];

@Injectable()
export class PermissionService {
    constructor(private principal: Principal) {}

    public puedeNavegarUserManagement(): boolean {
        return this.principal.rolesRutaMatchesRolesUsuario(USER_MANAGEMENT_ROLES);
    }

    public puedeNavegarHerramientas(): boolean {
        return this.principal.rolesRutaMatchesRolesUsuario(HERRAMIENTAS_ROLES);
    }

    public puedeNavegarAdministracion(): boolean {
        return this.principal.rolesRutaMatchesRolesUsuario(ADMINISTRACION_ROLES);
    }
}
