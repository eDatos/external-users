import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpEvent, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { LANG_KEY } from '@app/app.constants';
import { ConfigService } from '@app/config';

@Injectable()
export class LocaleInterceptor implements HttpInterceptor {
    constructor(private cookieService: CookieService, private configService: ConfigService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const locale = this.cookieService.get(LANG_KEY) || this.configService.getConfig().metadata.defaultLanguage;
        const urlWithLangQueryString = req.url.indexOf('?') != -1 ? `${req.url}&${LANG_KEY}=${locale}` : `${req.url}?${LANG_KEY}=${locale}`;
        return next.handle(req.clone({ url: urlWithLangQueryString }));
    }
}
