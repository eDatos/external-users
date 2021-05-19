import { Injectable } from '@angular/core';

import { Principal } from './principal.service';
import { AuthServerProvider } from './auth-jwt.service';

@Injectable()
export class LoginService {
    constructor(private principal: Principal, private authServerProvider: AuthServerProvider) {}

    logout() {
        return this.authServerProvider
            .logout()
            .toPromise()
            .then(() => {
                this.principal.authenticate(null);
            });
    }
}
