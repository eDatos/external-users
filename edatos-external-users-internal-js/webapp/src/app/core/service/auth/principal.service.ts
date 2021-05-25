import { Injectable } from '@angular/core';
import { Role, UserCAS } from '@app/core/model';
import { Subject, Observable } from 'rxjs';
import { AuthServerProvider } from './auth-jwt.service';

@Injectable()
export class Principal {
    [x: string]: any;
    private userIdentity: UserCAS | null;
    private authenticated = false;
    private authenticationState = new Subject<any>();

    constructor(private authServerProvider: AuthServerProvider) {}

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
        if (rolesRuta.length === 0) {
            return true;
        }
        if (!this.userIdentity || !this.userIdentity.roles) {
            return false;
        }
        return rolesRuta.some((rolRuta) => this.userIdentity?.hasRole(rolRuta));
    }

    identity(): Promise<UserCAS | null> {
        if (this.userIdentity) {
            return Promise.resolve(this.userIdentity);
        }

        const token: string = this.authServerProvider.getToken();
        if (token) {
            this.userIdentity = UserCAS.fromJwt(token);
            this.authenticated = true;
        } else {
            this.userIdentity = null;
            this.authenticated = false;
        }

        this.authenticationState.next(this.userIdentity);
        return Promise.resolve(this.userIdentity);
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

    public correctlyLogged(): boolean {
        return Boolean(this.userIdentity && this.userIdentity.roles.length !== 0);
    }
}
