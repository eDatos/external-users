import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { CookieService } from 'ngx-cookie';
import { TOKEN_AUTH_NAME, JHI_TOKEN_AUTH_NAME } from '@app/app.constants';

@Injectable()
export class AuthServerProvider {
    constructor(private $localStorage: LocalStorageService, private $sessionStorage: SessionStorageService, private cookieService: CookieService) {}

    getToken() {
        const token = this.$localStorage.retrieve(TOKEN_AUTH_NAME) || this.$sessionStorage.retrieve(TOKEN_AUTH_NAME);
        if (!token) {
            return this.cookieService.get(JHI_TOKEN_AUTH_NAME);
        }
        return token;
    }

    loginWithToken(jwt, rememberMe) {
        if (jwt) {
            this.storeAuthenticationToken(jwt, rememberMe);
            return Promise.resolve(jwt);
        } else {
            return Promise.reject('auth-jwt-service Promise reject'); // Put appropriate error message here
        }
    }

    storeAuthenticationToken(jwt, rememberMe) {
        if (rememberMe) {
            this.$localStorage.store(TOKEN_AUTH_NAME, jwt);
        } else {
            this.$sessionStorage.store(TOKEN_AUTH_NAME, jwt);
        }
    }

    logout(): Observable<any> {
        console.log('Hi! im there. In logout');
        return new Observable((observer) => {
            this.$localStorage.clear(TOKEN_AUTH_NAME);
            this.$sessionStorage.clear(TOKEN_AUTH_NAME);
            this.cookieService.remove(JHI_TOKEN_AUTH_NAME);
            console.log('LOCALSTORAGE', this.$localStorage);
            console.log('SESSIONSTORAGE', this.$sessionStorage);
            console.log('COOCKIESERVICE', this.cookieService);
            observer.complete();
        });
    }
}
