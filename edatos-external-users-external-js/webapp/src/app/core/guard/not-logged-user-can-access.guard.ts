import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, Data, CanLoad, Route } from '@angular/router';
import { DEFAULT_PATH } from '@app/app.constants';
import { Principal } from '../service/auth/principal.service';

@Injectable()
export class NotLoggedUserCanAccessGuard implements CanLoad, CanActivate {
    constructor(private router: Router, private principal: Principal) {}

    canActivate(route: ActivatedRouteSnapshot): Promise<boolean> {
        return this.canNavigateIsNotAuthenticated();
    }

    canLoad(route: Route): Promise<boolean> {
        return this.canNavigateIsNotAuthenticated();
    }

    private checkIsAuthenticated(): Promise<boolean> {
        return this.principal.identity().then((account) => {
            return !!account;
        });
    }

    private canNavigateIsNotAuthenticated(): Promise<boolean> {
        return this.checkIsAuthenticated().then((authenticated) => {
            if (authenticated) {
                this.router.navigate([DEFAULT_PATH]);
                return false;
            }
            return true;
        });
    }
}
