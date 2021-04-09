import { Injectable } from '@angular/core';

import { Principal } from './principal.service';
import { AuthServerProvider } from './auth-jwt.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class LoginService {
    constructor(private router: Router, private principal: Principal, private authServerProvider: AuthServerProvider) {}

    logout() {
        return this.authServerProvider
            .logout()
            .toPromise()
            .then(() => {
                this.principal.authenticate(null);
                this.router.navigate(['login']);
            });
    }
}
