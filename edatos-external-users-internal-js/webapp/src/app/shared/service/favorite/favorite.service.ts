import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { mapPlainToClass, mapPlainToResponseWrapper } from '@app/core/utils/response-utils';
import { Favorite } from '@app/shared/model/favorite.model';
import { ResponseWrapper } from 'arte-ng/model';
import { createRequestOption } from 'arte-ng/utils';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class FavoriteService {
    public resourceUrl = 'api/favorites';

    constructor(private http: HttpClient) {}

    public get(id: number): Observable<Favorite> {
        return this.http.get<Favorite>(`${this.resourceUrl}/${id}`).pipe(mapPlainToClass(Favorite));
    }

    public find(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get<Favorite>(this.resourceUrl, { ...options, observe: 'response' })
            .pipe(mapPlainToResponseWrapper(Favorite));
    }

    public save(filter: Favorite): Observable<Favorite> {
        return this.http.post<Favorite>(this.resourceUrl, filter).pipe(mapPlainToClass(Favorite));
    }

    public update(filter: Favorite): Observable<Favorite> {
        return this.http.put<Favorite>(this.resourceUrl, filter).pipe(mapPlainToClass(Favorite));
    }
}
