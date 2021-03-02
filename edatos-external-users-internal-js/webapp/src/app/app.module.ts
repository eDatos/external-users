import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { ArteTableModule, ArteTableService } from 'arte-ng';

import { NgxWebstorageModule } from 'ngx-webstorage';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { ConfigModule } from './config/config.module';
import { ConfigService } from './config';
import { SharedModule } from './shared';
import { TranslateModule, TranslateLoader, TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthServerProvider } from './core/service/auth';
import { AuthInterceptor, AuthExpiredInterceptor, ErrorHandlerInterceptor } from './core/interceptor';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { AppRoutingModule } from './app-routing.module';
import { NavbarComponent } from './layouts/navbar';
import { ErrorComponent, ErrorRoutingModule } from './layouts/error';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { CookieService } from 'ngx-cookie';
import { DEFAULT_LANG, LANG_KEY, AVAILABLE_LANGUAGES } from './app.constants';
import { ArteAlertService, ArteEventManager, PagingParamsResolver, ScrollService } from 'arte-ng/services';
import { EdatosNavbarComponent } from './layouts/edatos-navbar/edatos-navbar.component';

export function init(configService: ConfigService, authServerProvider: AuthServerProvider) {
  return () => {
    const promise: Promise<boolean> = new Promise((resolve, reject) => {
      if (authServerProvider.getToken()) {
        resolve(true);
      } else {
        const config = configService.getConfig();
        window.location.href = config.cas.login + '?service=' + encodeURIComponent(config.cas.service);
      }
    });
    return promise;
  };
}

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './i18n/', '.json');
}

export function initTranslations(translateService: TranslateService, cookieService: CookieService) {
  const currentLang: string = cookieService.get(LANG_KEY) || DEFAULT_LANG;
  translateService.setDefaultLang(DEFAULT_LANG);
  translateService.addLangs(AVAILABLE_LANGUAGES)
  return () => translateService.use(currentLang).toPromise();
}
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ErrorComponent,
    EdatosNavbarComponent,
  ],
  imports: [
    BrowserModule,
    ErrorRoutingModule,
    NgxWebstorageModule.forRoot({ prefix: 'ac', separator: '-' }),
    CoreModule,
    ConfigModule,
    SharedModule,
    AppRoutingModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    }),
    ToastModule,
    ServiceWorkerModule.register('./ngsw-worker.js', { enabled: environment.production }),
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: init,
      deps: [ConfigService, AuthServerProvider],
      multi: true
    },
    {
      provide: APP_INITIALIZER,
      useFactory: initTranslations,
      deps: [TranslateService, CookieService],
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    MessageService,
    ArteTableService,
    ArteEventManager,
    PagingParamsResolver,
    ArteAlertService,
    ScrollService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
