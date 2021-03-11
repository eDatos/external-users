import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '@app/core/model';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { createRequestOption, ResponseUtils } from 'arte-ng/src/lib/utils';
import { ResponseWrapper } from 'arte-ng/src/lib/model';

@Injectable()
export class AccountUserService {
    private resourceUrl = 'api/account';

    constructor(private http: HttpClient) {}

    create(user: User): Observable<User> {
        return this.http.post(`${this.resourceUrl}/signup`, user).pipe(map((res) => ResponseUtils.convert(res, User)));
    }

    buscarUsuarioPorEmail(email: string): Observable<User> {
        return this.http.get(`${this.resourceUrl}/${email}/findByEmail`).pipe(map((res) => ResponseUtils.convert(res, User)));
    }

    getLogueado(): Observable<User> {
        return this.http.get('api/usuario').pipe(map((res) => ResponseUtils.convert(res, User)));
    }

    update(user: User): Observable<User> {
        return this.http.put(this.resourceUrl, user).pipe(map((res) => ResponseUtils.convert(res, User)));
    }

    /* 
    get(login: string, includeDeleted = true): Observable<User> {
        const options = createRequestOption({ includeDeleted });
        return this.http.get(`${this.resourceUrl}/${login}`, options).pipe(map((res) => ResponseUtils.convert(res, User)));
    }

    find(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, { ...options, observe: 'response' }).pipe(map((res) => ResponseUtils.convertToResponseWrapper(res, User)));
    }

    delete(login: string): Observable<User> {
        return this.http.delete(`${this.resourceUrl}/${login}`).pipe(map((res) => ResponseUtils.convert(res, User)));
    }

    restore(login: string): Observable<User> {
        return this.http.put(`${this.resourceUrl}/${login}/restore`, null).pipe(map((res) => ResponseUtils.convert(res, User)));
    }
    */
}
