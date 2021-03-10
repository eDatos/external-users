import { BaseAuditingEntity } from 'arte-ng/src/lib/model';
import { Role } from './rol.model';

export class User extends BaseAuditingEntity {
    public id?: any;
    public name?: string;
    public surname1?: string;
    public surname2?: string;
    public password?: string;
    public email?: string;
    public organism?: string;
    public treatment?: any[];
    public lenguage?: any[];
    public phoneNumber?: string;
    public roles?: any[];

    constructor(
        id?: any,
        name?: string,
        surname1?: string,
        surname2?: string,
        email?: string,
        oranisgm?: string,
        password?: string,
        treatment?: any[],
        lenguage?: any[],
        phoneNumber?: string,
        roles?: any[]
    ) {
        super();
        this.id = id ? id : null;
        this.name = name ? name : null;
        this.surname1 = surname1 ? surname1 : null;
        this.surname2 = surname2 ? surname2 : null;
        this.email = email ? email : null;
        this.organism = oranisgm ? oranisgm : null;
        this.password = password ? password : null;
        this.phoneNumber = phoneNumber ? phoneNumber : null;
        this.roles = roles ? roles : null;
        this.treatment = treatment ? treatment : null;
        this.lenguage = lenguage ? lenguage : null;
    }

    public hasRole(rol: Role): boolean {
        return this.roles.some((userRol) => userRol.app == Role.USER && userRol.role == rol);
    }
}
