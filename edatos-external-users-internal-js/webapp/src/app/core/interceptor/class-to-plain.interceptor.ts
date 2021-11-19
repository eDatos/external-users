import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { classToPlain } from 'class-transformer';
import { Observable } from 'rxjs';

@Injectable()
export class ClassToPlainInterceptor implements HttpInterceptor {
    public intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        return next.handle(request.clone({ body: classToPlain(request.body) }));
    }
}