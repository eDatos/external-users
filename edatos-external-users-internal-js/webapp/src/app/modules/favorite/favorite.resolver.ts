import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Favorite, FavoriteService } from '@app/shared';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class FavoriteResolver implements Resolve<Favorite> {
    constructor(private filterService: FavoriteService) {}

    public resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Favorite> {
        const filterId = (route.paramMap.get('id') as unknown) as number;
        return this.filterService.get(filterId);
    }
}
