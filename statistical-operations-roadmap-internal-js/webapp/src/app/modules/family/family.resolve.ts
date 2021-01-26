import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { ActivatedRouteSnapshot } from '@angular/router';

import { Observable } from 'rxjs';

import { Family, FamilyService } from '@app/shared';

@Injectable()
export class FamilyResolve implements Resolve<Family> {
    constructor(private familyService: FamilyService) { }
    resolve(route: ActivatedRouteSnapshot): Observable<Family> {
        const paramsId = route.params['id'];
        return this.familyService.get(paramsId);
    }
}
