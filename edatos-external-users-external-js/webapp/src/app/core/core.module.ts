import { NgModule, Optional, SkipSelf, LOCALE_ID } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { Title } from '@angular/platform-browser';

import { CookieModule } from 'ngx-cookie';

import {
    AuthServerProvider,
    CSRFService,
    LoginService,
    PermissionService,
    Principal,
    StateStorageService,
    throwIfAlreadyLoaded,
    AccountUserService,
    PageTitleService,
    ProfileService,
    IssuesService
} from './service';
import { UserRouteAccessGuard } from './guard';
import localeEs from '@angular/common/locales/es';
import { registerLocaleData } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NotLoggedUserCanAccessGuard } from './guard/not-logged-user-can-access.guard';

registerLocaleData(localeEs, 'es');
@NgModule({
    imports: [BrowserAnimationsModule, HttpClientModule, CookieModule.forRoot()],
    providers: [
        AuthServerProvider,
        CSRFService,
        LoginService,
        PermissionService,
        Principal,
        StateStorageService,
        AccountUserService,
        UserRouteAccessGuard,
        NotLoggedUserCanAccessGuard,
        PageTitleService,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'es',
        },
        ProfileService,
        IssuesService
    ],
})
export class CoreModule {
    constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
        throwIfAlreadyLoaded(parentModule, 'CoreModule');
    }
}
