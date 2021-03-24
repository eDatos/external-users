import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Favorite } from '@app/shared/model/favorite.model';
import { ResponseWrapper } from 'arte-ng/model';
import { createRequestOption } from 'arte-ng/utils';
import { plainToClass } from 'class-transformer';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root',
})
export class FavoriteService {
    public resourceUrl = 'api/favorites';

    constructor(private http: HttpClient) {}

    public get(id: number): Observable<Favorite> {
        return this.http.get<Favorite>(`${this.resourceUrl}/${id}`).pipe(map((favorite) => this.convert(favorite)));
    }

    public find(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, { ...options, observe: 'response' }).pipe(map((response) => this.convertToResponseWrapper(response)));
    }

    public save(filter: Favorite): Observable<Favorite> {
        return this.http.post<Favorite>(this.resourceUrl, filter).pipe(map((favorite) => this.convert(favorite)));
    }

    public update(filter: Favorite): Observable<Favorite> {
        return this.http.put<Favorite>(this.resourceUrl, filter).pipe(map((favorite) => this.convert(favorite)));
    }

    private convert(favorite: Favorite) {
        return plainToClass(Favorite, favorite);
    }

    private convertToResponseWrapper(response: HttpResponse<any>) {
        const { headers, body, status } = response;
        const data = Array.isArray(body) ? body.map((favorite) => this.convert(favorite)) : this.convert(body);
        return new ResponseWrapper(headers, data, status);
    }
}
