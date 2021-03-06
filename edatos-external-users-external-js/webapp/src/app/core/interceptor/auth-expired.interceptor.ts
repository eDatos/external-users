import { Injector, Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthServerProvider } from '../service/auth';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
@Injectable({
    providedIn: 'root',
})
export class AuthExpiredInterceptor implements HttpInterceptor {
    constructor(private injector: Injector) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(
            catchError((error) => {
                if (error.status === 401) {
                    const authServerProvider: AuthServerProvider = this.injector.get(AuthServerProvider);
                    if(authServerProvider.getToken()) {
                        authServerProvider.logout().toPromise();
                    }
                }
                return throwError(error);
            })
        );
    }
}
