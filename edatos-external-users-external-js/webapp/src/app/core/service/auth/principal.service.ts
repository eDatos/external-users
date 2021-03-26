import { Injectable } from '@angular/core';
import { Role, UserAccount } from '@app/core/model';
import { Subject, Observable } from 'rxjs';
import { AuthServerProvider } from './auth-jwt.service';

@Injectable()
export class Principal {
    [x: string]: any;
    private userIdentity: UserAccount;
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

    identity(): Promise<UserAccount> {
        if (this.userIdentity) {
            return Promise.resolve(this.userIdentity);
        }

        const token: string = this.authServerProvider.getToken();
        console.log('token', token);
        if (token) {
            this.userIdentity = UserAccount.fromJwt(token);
            this.authenticated = true;
        } else {
            this.userIdentity = null;
            this.authenticated = false;
        }

        this.authenticationState.next(this.userIdentity);
        console.log('userIdentity', this.userIdentity);
        return Promise.resolve(this.userIdentity);
    }

    public correctlyLogged(): boolean {
        return Boolean(this.userIdentity && this.userIdentity.roles.length !== 0);
    }
}
