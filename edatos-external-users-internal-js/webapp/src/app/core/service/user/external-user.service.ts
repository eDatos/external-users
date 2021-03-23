import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ExternalUser } from '@app/core/model';
import { ResponseWrapper } from 'arte-ng/model';
import { createRequestOption, ResponseUtils } from 'arte-ng/utils';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class ExternalUserService {
    private resourceUrl = 'api/external-users';

    constructor(private http: HttpClient) {}

    public create(user: ExternalUser): Observable<ExternalUser> {
        return this.http.post(this.resourceUrl, user).pipe(map((res) => ResponseUtils.convert(res, ExternalUser)));
    }

    public update(user: ExternalUser): Observable<ExternalUser> {
        return this.http.put(this.resourceUrl, user).pipe(map((res) => ResponseUtils.convert(res, ExternalUser)));
    }

    public get(id: number, includeDeleted = true): Observable<ExternalUser> {
        const options = createRequestOption({ includeDeleted });
        return this.http.get(`${this.resourceUrl}/${id}`, options).pipe(map((res) => ResponseUtils.convert(res, ExternalUser)));
    }

    public find(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, { ...options, observe: 'response' }).pipe(map((res) => ResponseUtils.convertToResponseWrapper(res, ExternalUser)));
    }

    public delete(id: string): Observable<ExternalUser> {
        return this.http.delete(`${this.resourceUrl}/${id}`).pipe(map((res) => ResponseUtils.convert(res, ExternalUser)));
    }

    public restore(id: string): Observable<ExternalUser> {
        return this.http.put(`${this.resourceUrl}/${id}/restore`, null).pipe(map((res) => ResponseUtils.convert(res, ExternalUser)));
    }
}
