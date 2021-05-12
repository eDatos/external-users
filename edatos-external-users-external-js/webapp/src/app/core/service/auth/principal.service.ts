import { Injectable } from '@angular/core';
import { Role, UserAccount } from '@app/core/model';
import { Subject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthServerProvider } from './auth-jwt.service';

@Injectable()
export class Principal {
    [x: string]: any;
    private userIdentity: UserAccount;
    private authenticationState = new Subject<UserAccount>();

    constructor(private authServerProvider: AuthServerProvider) {}

    authenticate(identity) {
        this.userIdentity = identity;
        this.authenticationState.next(this.userIdentity);
    }

    hasRoles(rolesRuta: Role[]): Promise<boolean> {
        return Promise.resolve(this.rolesRutaMatchesRolesUsuario(rolesRuta));
    }

    rolesRutaMatchesRolesUsuario(rolesRuta: Role[]) {
        return true;
    }

    isAuthenticated(): Observable<boolean> {
        return this.getAuthenticationState().pipe(map((userAccount) => userAccount !== null));
    }

    isIdentityResolved(): boolean {
        return this.userIdentity !== undefined;
    }

    getAuthenticationState(): Observable<UserAccount> {
        return this.authenticationState.asObservable();
    }

    identity(): Promise<UserAccount> {
        if (this.userIdentity) {
            return Promise.resolve(this.userIdentity);
        }

        const token: string = this.authServerProvider.getToken();
        if (token) {
            this.userIdentity = UserAccount.fromJwt(token);
        } else {
            this.userIdentity = null;
        }

        this.authenticationState.next(this.userIdentity);
        return Promise.resolve(this.userIdentity);
    }

    public isCorrectlyLogged(): Observable<boolean> {
        return this.getAuthenticationState().pipe(map((userAccount) => userAccount && userAccount.roles && !!userAccount.roles.length));
    }
}
