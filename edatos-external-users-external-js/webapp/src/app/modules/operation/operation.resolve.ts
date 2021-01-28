import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { ActivatedRouteSnapshot } from '@angular/router';

import { Observable } from 'rxjs';

import { Operation, OperationService } from '@app/shared';

@Injectable()
export class OperationResolve implements Resolve<Operation> {
    constructor(private operationService: OperationService) { }
    resolve(route: ActivatedRouteSnapshot): Observable<Operation> {
        const paramsId = route.params['id'];
        return this.operationService.get(paramsId);
    }
}
