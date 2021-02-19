import { Injectable } from '@angular/core';
import { Role } from '@app/core/model';
import { Subject, Observable } from 'rxjs';
import { AuthServerProvider } from './auth-jwt.service';

@Injectable()
export class Principal {
    [x: string]: any;
    private userIdentity: Role;
    private authenticated = false;
    private authenticationState = new Subject<any>();

    constructor(private authServerProvider: AuthServerProvider) { }

    authenticate(identity) {
        this.userIdentity = identity;
        this.authenticated = identity !== null;
        this.authenticationState.next(this.userIdentity);
    }

    hasRoles(rolesRuta: Role[]): Promise<boolean> {
        return Promise.resolve(this.rolesRutaMatchesRolesUsuario(rolesRuta));
    }

    rolesRutaMatchesRolesUsuario(rolesRuta: Role[]) {
        rolesRuta = rolesRuta || [];
     /*   if (rolesRuta.length === 0) { // TODO EDATOS-3266
            return true;
        }
*/
        return true;
    }

    isAuthenticated(): boolean {
        return this.authenticated;
    }

    isIdentityResolved(): boolean {
        return this.userIdentity !== undefined;
    }

    getAuthenticationState(): Observable<any> {
        return this.authenticationState.asObservable();
    }

 /*   public correctlyLogged(): boolean {
        return Boolean(this.userIdentity && this.userIdentity.roles.length !== 0);
    }*/
}
