import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanLoad, Data, Route, Router } from '@angular/router';
import { Role } from '../model/rol.model';
import { LoginService } from '../service/auth/login.service';
import { Principal } from '../service/auth/principal.service';

@Injectable()
export class UserRouteAccessGuard implements CanActivate, CanLoad {
    public static AUTH_REDIRECT = 'authRedirect';
    private static ROLES = 'roles';

    constructor(private router: Router, private principal: Principal, private loginService: LoginService) {}

    public canActivate(route: ActivatedRouteSnapshot): boolean | Promise<boolean> {
        const roles = this.rolesFromRouteSnapshot(route);
        return this.checkRoles(roles, route.data);
    }

    public canLoad(route: Route): true | Promise<boolean> {
        const roles = route.data ? route.data.roles : [];
        return this.checkRoles(roles, route.data!);
    }

    public checkLogin(roles: Role[]): Promise<boolean> {
        return this.principal.identity().then((account) => {
            if (!!account) {
                return this.noPermissionRequired(roles) || this.principal.hasRoles(roles);
            } else {
                // User is not logged in, redirect to CAS
                this.loginService.loginInCas();
                return false;
            }
        });
    }

    private checkRoles(roles, routeData: Data): true | Promise<boolean> {
        return this.checkLogin(roles).then((canActivate) => {
            if (!canActivate) {
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
            this.router.navigateByUrl('/accessdenied');
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
