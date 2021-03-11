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
} from './service';
import { UserRouteAccessGuard } from './guard';
import localeEs from '@angular/common/locales/es';
import { registerLocaleData } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

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
        PageTitleService,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'es',
        },
        ProfileService,
    ],
})
export class CoreModule {
    constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
        throwIfAlreadyLoaded(parentModule, 'CoreModule');
    }
}
