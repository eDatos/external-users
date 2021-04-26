import { Injectable } from '@angular/core';
import { Principal } from './principal.service';
import { Role } from '@app/core/model';

export const USER = [Role.USER];
export const HERRAMIENTAS_ROLES = [Role.HERRAMIENTAS_ROLES];
export const ALL_ALLOWED = [Role.ANY_ROLE_ALLOWED];

@Injectable()
export class PermissionService {
    constructor(private principal: Principal) {}

    puedeNavegarUserManagement(): boolean {
        return this.principal.rolesRutaMatchesRolesUsuario(ALL_ALLOWED);
    }

    puedeNavegarHerramientas(): boolean {
        return this.principal.rolesRutaMatchesRolesUsuario(HERRAMIENTAS_ROLES);
    }

    puedeNavegarAdministracion(): boolean {
        return this.principal.rolesRutaMatchesRolesUsuario(ALL_ALLOWED);
    }
}
