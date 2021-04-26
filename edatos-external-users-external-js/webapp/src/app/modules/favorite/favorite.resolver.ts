import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { Favorite } from '@app/shared';
import { Observable } from 'rxjs';
import { FavoriteService } from '@app/shared/service';

@Injectable()
export class FavoritesResolver implements Resolve<Favorite[]> {
    constructor(private favoriteService: FavoriteService) {}
    resolve(route: ActivatedRouteSnapshot): Observable<Favorite[]> {
        return this.favoriteService.findByUserId();
    }
}
