import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { CookieService } from 'ngx-cookie-service';

import { TOKEN_AUTH_NAME } from '@app/app.constants';
import { ConfigService } from '@app/config';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class AuthInterceptor implements HttpInterceptor {
    constructor(private localStorage: LocalStorageService, private sessionStorage: SessionStorageService, private cookieService: CookieService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let headers: HttpHeaders = req.headers;

        const token = this.localStorage.retrieve(TOKEN_AUTH_NAME) || this.sessionStorage.retrieve(TOKEN_AUTH_NAME);

        if (!!token) {
            headers = headers.append('Authorization', 'Bearer ' + token);
        } else {
            const tokenFromCookie = this.cookieService.get(TOKEN_AUTH_NAME);
            if (!!tokenFromCookie) {
                this.storeAuthenticationToken(tokenFromCookie, false);
                headers = headers.append('Authorization', 'Bearer ' + tokenFromCookie);

                /*
                Si se borrar la cookie y el token se guarda en session storage, al abrir una nueva pestaña en el navegador la primera petición a la API dará un 401
                y en consecuencia el navegador redireccionará a la ruta raíz de la aplicación.
                */
                //const config = this.configService.getConfig();
                //this.cookieService.remove(TOKEN_AUTH_NAME, { path: this.getLocation(config.endpoint.appUrl).pathname });
            }
        }

        return next.handle(req.clone({ headers })).pipe(
            map((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                    const jwt = event.headers.get(TOKEN_AUTH_NAME);
                    if (!!jwt) {
                        this.storeAuthenticationToken(jwt, false);
                    }
                }
                return event;
            })
        );
    }

    private storeAuthenticationToken(jwt, rememberMe) {
        if (rememberMe) {
            this.localStorage.store(TOKEN_AUTH_NAME, jwt);
        } else {
            this.sessionStorage.store(TOKEN_AUTH_NAME, jwt);
        }
    }
}
