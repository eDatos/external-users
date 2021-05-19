import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Issues } from '@app/core/model';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { convert } from '@app/core/utils/response-utils';

@Injectable()
export class IssuesService {

    private resourceUrl = 'api/issues';

    constructor(private http: HttpClient) {}

    create(issue: Issues): Observable<Issues> {
        return this.http.post<Issues>(`${this.resourceUrl}/create`, issue).pipe(map((res) => convert(Issues, res)));
    }
}