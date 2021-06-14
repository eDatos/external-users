import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { convert } from '@app/core/utils/response-utils';
import { ExternalOperation } from '@app/shared/model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root',
})
export class ExternalOperationService {
    public readonly resourceUrl = 'api/external-operations';

    constructor(private http: HttpClient) {}

    public updateNotifications(externalOperation: ExternalOperation): Observable<ExternalOperation> {
        return this.http.put<ExternalOperation>(this.resourceUrl, externalOperation).pipe(map((externalOp) => convert(ExternalOperation, externalOp)));
    }
}
