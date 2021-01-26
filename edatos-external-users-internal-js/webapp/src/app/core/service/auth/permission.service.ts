import { Injectable } from '@angular/core';
import { Principal } from './principal.service';
import { Rol } from '@app/core/model';

export const USER_MANAGEMENT_ROLES = [Rol.ADMINISTRADOR];
export const HERRAMIENTAS_ROLES = [Rol.ADMINISTRADOR];
export const ADMINISTRACION_ROLES = [Rol.ADMINISTRADOR];
export const FAMILY_ROLES = [Rol.ADMINISTRADOR];
export const OPERATION_ROLES = [Rol.ADMINISTRADOR];

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
