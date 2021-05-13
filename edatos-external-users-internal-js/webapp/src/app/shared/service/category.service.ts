import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { convert } from '@app/core/utils/response-utils';
import { Category, ExternalCategory } from '@app/shared/model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root',
})
export class CategoryService {
    public readonly resourceUrl = 'api/categories';

    constructor(private http: HttpClient) {}

    public get(id: number): Observable<Category> {
        return this.http.get<Category>(`${this.resourceUrl}/${id}`).pipe(map((category) => convert(Category, category)));
    }

    public findAll(): Observable<Category[]> {
        return this.http.get<Category[]>(`${this.resourceUrl}`).pipe(map((category) => convert(Category, category)));
    }

    public getTree(): Observable<Category[]> {
        return this.http
            .get<Category[]>(`${this.resourceUrl}/tree`, { params: { sort: 'id' } })
            .pipe(map((tree) => convert(Category, tree)));
    }

    public updateTree(categories: Category[]): Observable<Category[]> {
        return this.http.put<Category[]>(`${this.resourceUrl}/tree`, categories).pipe(map((cat) => convert(Category, cat)));
    }

    public createCategory(category: Category, parent?: Category): Observable<Category> {
        let options = {};
        if (parent) {
            options = { params: { parentId: parent.id } };
        }
        return this.http.post<Category>(this.resourceUrl, category, options).pipe(map((cat) => convert(Category, cat)));
    }

    public updateCategory(category: Category, parent?: Category): Observable<Category> {
        let options = {};
        if (parent) {
            options = { params: { parentId: parent.id } };
        }
        return this.http.put<Category>(this.resourceUrl, category, options).pipe(map((cat) => convert(Category, cat)));
    }

    public deleteCategory(id: number): Observable<HttpResponse<void>> {
        return this.http.delete<void>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    public getExternalCategories(): Observable<ExternalCategory[]> {
        return this.http.get<ExternalCategory[]>(`${this.resourceUrl}/external-categories`).pipe(map((externalCategories) => convert(ExternalCategory, externalCategories)));
    }
}
