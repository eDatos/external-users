import { Injectable } from '@angular/core';

import { Principal } from './principal.service';
import { AuthServerProvider } from './auth-jwt.service';
import { ConfigService } from '@app/config/config.service';

@Injectable()
export class LoginService {
    constructor(private principal: Principal, private authServerProvider: AuthServerProvider, private configService: ConfigService) {}

    logout() {
        this.authServerProvider.logout().subscribe();
        this.principal.authenticate(null);
    }

}
