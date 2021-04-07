import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Filter } from '@app/core/model';
import { ResponseWrapper } from 'arte-ng/model';
import { createRequestOption, ResponseUtils } from 'arte-ng/utils';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root',
})
export class FilterService {
    public readonly resourceUrl = 'api/filters';

    constructor(private http: HttpClient) {}

    public get(id: number): Observable<Filter> {
        return this.http.get<Filter>(`${this.resourceUrl}/${id}`);
    }

    public find(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, { ...options, observe: 'response' }).pipe(map((res) => ResponseUtils.convertToResponseWrapper(res, Filter)));
    }

    public save(filter: Filter): Observable<Filter> {
        return this.http.post<Filter>(this.resourceUrl, filter);
    }

    public update(filter: Filter): Observable<Filter> {
        return this.http.put<Filter>(this.resourceUrl, filter);
    }
}
