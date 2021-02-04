import { Injectable } from '@angular/core';
import { Principal } from './principal.service';
import { Role } from '@app/core/model';

export const USER_MANAGEMENT_ROLES = [Role.ADMINISTRADOR];
export const HERRAMIENTAS_ROLES = [Role.ADMINISTRADOR];
export const ADMINISTRACION_ROLES = [Role.ADMINISTRADOR];
export const FAMILY_ROLES = [Role.ADMINISTRADOR];
export const OPERATION_ROLES = [Role.ADMINISTRADOR];

@Injectable()
export class PermissionService {
    constructor(private principal: Principal) {}

    puedeNavegarUserManagement(): boolean {
        return this.principal.rolesRutaMatchesRolesUsuario(USER_MANAGEMENT_ROLES);
    }

    puedeNavegarHerramientas(): boolean {
        return this.principal.rolesRutaMatchesRolesUsuario(HERRAMIENTAS_ROLES);
    }

    puedeNavegarAdministracion(): boolean {
        return this.principal.rolesRutaMatchesRolesUsuario(ADMINISTRACION_ROLES);
    }
}
