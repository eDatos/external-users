import { BaseAuditingEntity } from 'arte-ng/src/lib/model';
import * as jwtDecode from 'jwt-decode';
import { Rol } from './rol.model';

export class User extends BaseAuditingEntity {
    public id?: any;
    public login?: string;
    public nombre?: string;
    public apellido1?: string;
    public apellido2?: string;
    public email?: string;
    public roles?: any[];

    constructor(id?: any, login?: string, nombre?: string, apellido1?: string, apellido2?: string, email?: string, roles?: any[]) {
        super();
        this.id = id ? id : null;
        this.login = login ? login : null;
        this.nombre = nombre ? nombre : null;
        this.apellido1 = apellido1 ? apellido1 : null;
        this.apellido2 = apellido2 ? apellido2 : null;
        this.email = email ? email : null;
        this.roles = roles ? roles : null;
    }
}

export interface RolCAS {
    app: string;
    role: string;
}

export class UserCAS {
    
    // TODO EDATOS-3141 Cambiar nombre de aplicación
    private readonly ACL_APP_NAME: string = "GESTOR_OPERACIONES"
    constructor(public login: string, public roles: RolCAS[]) {}

    public static fromJwt(token: string) {
        const payload: {sub: string, auth: string, exp: string} = jwtDecode(token)
        const rolesCas = payload.auth.split(',').map(appRole => {
            const [app, role] = appRole.split('#', 2);
            return {app, role} as RolCAS;
        });
        return new UserCAS(payload.sub, rolesCas);
    }

    public hasRole(rol: Rol): boolean {
        return this.roles.some(userRol => userRol.app == this.ACL_APP_NAME && userRol.role == rol);
    }
}