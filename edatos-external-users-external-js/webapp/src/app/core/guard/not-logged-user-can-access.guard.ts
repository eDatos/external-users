import { DOCUMENT } from '@angular/common';
import { Inject, Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, CanLoad, Route, Params, UrlSegment } from '@angular/router';
import { DEFAULT_PATH } from '@app/app.constants';
import { addQueryParamToRoute } from '@app/shared/utils/routesUtils';
import { AuthServerProvider } from '../service';
import { Principal } from '../service/auth/principal.service';

@Injectable()
export class NotLoggedUserCanAccessGuard implements CanLoad, CanActivate {
    constructor(private router: Router, private principal: Principal, private authServerProvider: AuthServerProvider, @Inject(DOCUMENT) readonly document: Document) {}

    canActivate(route: ActivatedRouteSnapshot): Promise<boolean> {
        return this.canNavigateIsNotAuthenticated(route.queryParams);
    }

    canLoad(route: Route): Promise<boolean> {
        return this.canNavigateIsNotAuthenticated(this.router.getCurrentNavigation().extractedUrl.queryParams);
    }

    private checkIsAuthenticated(): Promise<boolean> {
        return this.principal.identity().then((account) => {
            return !!account;
        });
    }

    private canNavigateIsNotAuthenticated(params?: Params): Promise<boolean> {
        return this.checkIsAuthenticated().then((authenticated) => {
            const origin = params && params['origin'] ? params['origin'].replace(/^http:\/\//i, 'https://') : undefined;
            if (authenticated) {
                if (origin) {
                    const originRouteWithTokenParam = addQueryParamToRoute(origin, 'token', encodeURIComponent(this.authServerProvider.getToken()));
                    this.document.defaultView.open(originRouteWithTokenParam, '_self');
                } else {
                    this.router.navigate([DEFAULT_PATH]);
                }
                return false;
            }
            return true;
        });
    }
}
