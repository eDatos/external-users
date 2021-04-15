import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Credentials, User, UserAccountChangePassword } from '@app/core/model';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { createRequestOption, ResponseUtils } from 'arte-ng/utils';
import { ResponseWrapper } from 'arte-ng/model';

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
        return this.http.get(`${this.resourceUrl}`).pipe(map((res) => ResponseUtils.convert(res, User)));
    }

    update(user: User): Observable<User> {
        return this.http.put(`${this.resourceUrl}`, user).pipe(map((res) => ResponseUtils.convert(res, User)));
    }

    login(credentials: Credentials): Observable<User> {
        return this.http.post(`api/login`, credentials).pipe(map((res) => ResponseUtils.convert(res, User)));
    }

    changeCurrentUserPassword(accountChangePassword: UserAccountChangePassword): Observable<User> {
        return this.http.post(`${this.resourceUrl}/change-password`, accountChangePassword).pipe(map((res) => ResponseUtils.convert(res, User)));
    }

    delete(id: number): Observable<User> {
        return this.http.delete(`${this.resourceUrl}/${id}`).pipe(map((res) => ResponseUtils.convert(res, User)));
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

    restore(login: string): Observable<User> {
        return this.http.put(`${this.resourceUrl}/${login}/restore`, null).pipe(map((res) => ResponseUtils.convert(res, User)));
    }
    */
}
