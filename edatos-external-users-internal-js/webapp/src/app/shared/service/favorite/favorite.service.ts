import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { convert, ResponseWrapper } from '@app/core/utils/response-utils';
import { Favorite } from '@app/shared/model/favorite.model';
import { createRequestOption } from 'arte-ng/utils';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root',
})
export class FavoriteService {
    public resourceUrl = 'api/favorites';

    constructor(private http: HttpClient) {}

    public get(id: number): Observable<Favorite> {
        return this.http.get<Favorite>(`${this.resourceUrl}/${id}`).pipe(map((favorite) => convert(Favorite, favorite)));
    }

    public find(req?: any): Observable<ResponseWrapper<Favorite[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<Favorite[]>(this.resourceUrl, { ...options, observe: 'response' })
            .pipe(map((favorite) => convert(Favorite, favorite)));
    }

    public findByUserId(id: number): Observable<Favorite[]> {
        return this.http
            .get<Favorite[]>(this.resourceUrl, { params: { query: `USER_ID EQ ${id}` } })
            .pipe(map((favorite) => convert(Favorite, favorite)));
    }

    public save(filter: Favorite): Observable<Favorite> {
        return this.http.post<Favorite>(this.resourceUrl, filter).pipe(map((favorite) => convert(Favorite, favorite)));
    }

    public update(filter: Favorite): Observable<Favorite> {
        return this.http.put<Favorite>(this.resourceUrl, filter).pipe(map((favorite) => convert(Favorite, favorite)));
    }
}
