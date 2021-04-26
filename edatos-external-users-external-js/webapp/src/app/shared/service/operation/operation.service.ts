import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { convert } from '@app/core/utils/response-utils';
import { Operation } from '@app/shared/model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root',
})
export class OperationService {
    public readonly resourceUrl = 'api/operations';

    constructor(private http: HttpClient) {}

    public get(id: number): Observable<Operation> {
        return this.http.get<Operation>(`${this.resourceUrl}/${id}`).pipe(map((category) => convert(Operation, category)));
    }

    public find(req: { [key: string]: any }): Observable<Operation[]> {
        return this.http
            .get<Operation[]>(`${this.resourceUrl}`, { params: req })
            .pipe(map((category) => convert(Operation, category)));
    }
}
