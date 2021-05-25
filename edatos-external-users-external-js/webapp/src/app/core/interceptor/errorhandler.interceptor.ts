import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { EUsuariosAlertService } from '../service';
import { Router } from '@angular/router';

const HTTP_ERROR_REDIRECT = {
    403: ['accessdenied'],
    404: ['notfound'],
};

@Injectable({
    providedIn: 'root',
})
export class ErrorHandlerInterceptor implements HttpInterceptor {
    constructor(private alertService: EUsuariosAlertService, private router: Router) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next
            .handle(req)
            .pipe(
                catchError((err) => {
                    if (err instanceof HttpErrorResponse && err.error instanceof Blob) {
                        const reader: FileReader = new FileReader();

                        const obs = new Observable<HttpEvent<any>>((observer: any) => {
                            reader.onloadend = (e) => {
                                const errorMessage = JSON.parse(reader.result as string);
                                const errorResponse: HttpErrorResponse = new HttpErrorResponse({
                                    error: errorMessage,
                                    headers: err.headers,
                                    status: err.status,
                                    statusText: err.statusText,
                                    url: err.url,
                                });
                                observer.error(errorResponse);
                                observer.complete();
                            };
                        });
                        reader.readAsText(err.error);
                        return obs;
                    }
                    return throwError(err);
                })
            )
            .pipe(
                catchError((err) => {
                    if (HTTP_ERROR_REDIRECT.hasOwnProperty(err.status)) {
                        this.router.navigate(HTTP_ERROR_REDIRECT[err.status]);
                    } else if (err.status != 401) {
                        this.alertService.handleResponseError(err);
                    }
                    return throwError(err);
                })
            );
    }
}
