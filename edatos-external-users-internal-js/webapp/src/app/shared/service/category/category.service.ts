import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { convert } from '@app/core/utils/response-utils';
import { Category } from '@app/shared/model';
import { StructuralResourcesTree } from '@app/shared/model/structural-resources-tree.model';
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

    public getTree(): Observable<StructuralResourcesTree[]> {
        return this.http
            .get<StructuralResourcesTree[]>(`${this.resourceUrl}/tree`, { params: { sort: 'id' } })
            .pipe(map((tree) => convert(StructuralResourcesTree, tree)));
    }
}
