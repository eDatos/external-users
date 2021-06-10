import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { convert, ResponseWrapper } from '@app/core/utils/response-utils';
import { Category, ExternalCategory, ExternalOperation } from '@app/shared/model';
import { Observable } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';
import { createRequestOption } from 'arte-ng/utils';

@Injectable({
    providedIn: 'root',
})
export class CategoryService {
    public readonly resourceUrl = 'api/categories';

    constructor(private http: HttpClient) {}

    public get(id: number): Observable<Category> {
        return this.http.get<Category>(`${this.resourceUrl}/${id}`).pipe(map((category) => convert(Category, category)));
    }

    public findAll(options?: {}): Observable<ResponseWrapper<Category[]>> {
        const params = createRequestOption(options);
        return this.http
            .get<Category[]>(`${this.resourceUrl}`, { ...params, observe: 'response' })
            .pipe(map((category) => convert(Category, category)));
    }

    public getTree(): Observable<Category[]> {
        return this.http
            .get<Category[]>(`${this.resourceUrl}/tree`, { params: { sort: 'id' } })
            .pipe(map((tree) => convert(Category, tree)));
    }

    public updateTree(categories: Category[]): Observable<Category[]> {
        return this.http.put<Category[]>(`${this.resourceUrl}/tree`, categories).pipe(map((cat) => convert(Category, cat)));
    }

    public deleteCategory(id: number): Observable<HttpResponse<void>> {
        return this.http.delete<void>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    public getExternalCategories(params?: { page?: string; offset?: string; limit?: string }): Observable<ResponseWrapper<ExternalCategory[]>> {
        return (
            this.http
                // @ts-ignore
                .get<ExternalCategory[]>(`${this.resourceUrl}/external-categories`, { observe: 'response', params })
                .pipe(map((externalCategories) => convert(ExternalCategory, externalCategories)))
        );
    }
}
