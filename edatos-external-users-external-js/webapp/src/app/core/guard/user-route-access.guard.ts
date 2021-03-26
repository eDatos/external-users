import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, Data, CanLoad, Route } from '@angular/router';
import { Principal } from '../service/auth/principal.service';
import { Role } from '../model/rol.model';
import { LoginService } from '../service/auth/login.service';

@Injectable()
export class UserRouteAccessGuard implements CanActivate, CanLoad {
    public static AUTH_REDIRECT = 'authRedirect';
    private static ROLES = 'roles';

    constructor(private router: Router, private principal: Principal, private loginService: LoginService) {}

    canActivate(route: ActivatedRouteSnapshot): boolean | Promise<boolean> {
        console.log('route', route);
        debugger;
        const roles = this.rolesFromRouteSnapshot(route);
        return this.checkRoles(roles, route.data);
    }

    canLoad(route: Route): true | Promise<boolean> {
        console.log('route', route);
        const roles = route.data ? route.data.roles : [];
        return this.checkRoles(roles, route.data);
    }

    checkLogin(): Promise<boolean> {
        return this.principal.identity().then((account) => {
            return !!account;
        });
    }

    private checkRoles(roles, routeData: Data): true | Promise<boolean> {
        return this.checkLogin().then((isLogged) => {
            if (!isLogged) {
                this.router.navigate(['login']);
                return false;
            }

            if (!this.noPermissionRequired(roles) && !this.principal.hasRoles(roles)) {
                this.redirect(routeData);
                return false;
            }

            return true;
        });
    }

    private noPermissionRequired(roles: Role[]) {
        return !roles || roles.length === 0;
    }

    private redirect(data: Data) {
        if (data[UserRouteAccessGuard.AUTH_REDIRECT]) {
            this.router.navigate([data[UserRouteAccessGuard.AUTH_REDIRECT]]);
        } else {
            this.router.navigate(['accessdenied']);
        }
    }

    private rolesFromRouteSnapshot(route: ActivatedRouteSnapshot): Role[] {
        if (route.firstChild && route.firstChild.data && route.firstChild.data[UserRouteAccessGuard.ROLES]) {
            return this.rolesFromRoute(route.firstChild);
        } else {
            return this.rolesFromRoute(route);
        }
    }

    private rolesFromRoute(route): Role[] {
        return route.data[UserRouteAccessGuard.ROLES];
    }
}
