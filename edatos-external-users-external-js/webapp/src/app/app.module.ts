import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ServiceWorkerModule } from '@angular/service-worker';
import { LanguageService } from '@app/shared/service';
import { MissingTranslationHandler, TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { ArteTableService } from 'arte-ng';
import { ArteEventManager, PagingParamsResolver, ScrollService } from 'arte-ng/services';
import { CookieService } from 'ngx-cookie-service';
import { ScriptLoaderModule } from 'ngx-script-loader';

import { NgxWebstorageModule } from 'ngx-webstorage';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { environment } from '../environments/environment';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AVAILABLE_LANGUAGES, DEFAULT_LANG, LANG_KEY } from './app.constants';
import { ConfigService } from './config';
import { ConfigModule } from './config/config.module';
import { MissingTranslationHandlerImpl } from './config/missing-translation-handler-impl';
import { CoreModule } from './core/core.module';
import { AuthExpiredInterceptor, AuthInterceptor, ErrorHandlerInterceptor, LocaleInterceptor } from './core/interceptor';
import { ErrorComponent, ErrorRoutingModule } from './layouts/error';
import { NavbarComponent } from './layouts/navbar';
import { AboutDialogComponent, DeleteConfirmDialogComponent } from './modules/account';
import { SharedModule } from './shared';

export function createTranslateLoader(http: HttpClient) {
    return new TranslateHttpLoader(http, './i18n/', '.json');
}

export function initTranslations(translateService: TranslateService, cookieService: CookieService) {
    const currentLang: string = cookieService.get(LANG_KEY) || DEFAULT_LANG;
    translateService.setDefaultLang(DEFAULT_LANG);
    translateService.addLangs(AVAILABLE_LANGUAGES);
    return () => translateService.use(currentLang).toPromise();
}

@NgModule({
    declarations: [AppComponent, NavbarComponent, ErrorComponent, DeleteConfirmDialogComponent, AboutDialogComponent],
    entryComponents: [DeleteConfirmDialogComponent, AboutDialogComponent],
    imports: [
        BrowserModule,
        ErrorRoutingModule,
        NgxWebstorageModule.forRoot({ prefix: 'ac', separator: '-' }),
        CoreModule,
        ConfigModule,
        SharedModule,
        AppRoutingModule,
        TranslateModule.forRoot({
            missingTranslationHandler: { provide: MissingTranslationHandler, useClass: MissingTranslationHandlerImpl },
            loader: {
                provide: TranslateLoader,
                useFactory: createTranslateLoader,
                deps: [HttpClient],
            },
        }),
        ToastModule,
        ServiceWorkerModule.register('./ngsw-worker.js', { enabled: environment.production }),
        ScriptLoaderModule,
    ],
    providers: [
        {
            provide: APP_INITIALIZER,
            useFactory: initTranslations,
            deps: [TranslateService, CookieService],
            multi: true,
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
        },
        {
            provide: APP_INITIALIZER,
            useFactory: (languageService: LanguageService) => () => languageService.init(),
            deps: [LanguageService],
            multi: true,
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: LocaleInterceptor,
            deps: [CookieService, ConfigService],
            multi: true,
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
        },
        MessageService,
        ArteTableService,
        ArteEventManager,
        PagingParamsResolver,
        ScrollService,
    ],
    bootstrap: [AppComponent],
})
export class AppModule {}
