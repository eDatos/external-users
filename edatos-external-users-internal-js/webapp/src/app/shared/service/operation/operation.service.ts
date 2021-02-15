import { Injectable } from '@angular/core';

import { ResponseUtils, createRequestOption } from 'arte-ng/utils';
import { Operation } from '@app/shared/model';
import { ResponseWrapper } from 'arte-ng/model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable()
export class OperationService {
    private resourceUrl = 'api/operations';

    constructor(private http: HttpClient) { }

    create(operation: Operation): Observable<Operation> {
        return this.http.post(this.resourceUrl, operation).pipe(map((res) => ResponseUtils.convert(res, Operation)));
    }

    update(operation: Operation): Observable<Operation> {
        return this.http.put(this.resourceUrl, operation).pipe(map((res) => ResponseUtils.convert(res, Operation)));
    }

    get(id: number): Observable<Operation> {
        return this.http.get(`${this.resourceUrl}/${id}`).pipe(map((res) => ResponseUtils.convert(res, Operation)));
    }

    find(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, { ...options, observe: 'response' }).pipe(map((res) => ResponseUtils.convertToResponseWrapper(res, Operation)));
    }

    delete(id: number): Observable<Operation> {
        return this.http.delete(`${this.resourceUrl}/${id}`).pipe(map((res) => ResponseUtils.convert(res, Operation)));
    }

    publishInternally(id: number): Observable<Operation> {
        return this.http.post(`${this.resourceUrl}/${id}/publish-internally`, id).pipe(map((res) => ResponseUtils.convert(res, Operation)));
    }

    publishExternally(id: number): Observable<Operation> {
        return this.http.post(`${this.resourceUrl}/${id}/publish-externally`, id).pipe(map((res) => ResponseUtils.convert(res, Operation)));
    }
}
