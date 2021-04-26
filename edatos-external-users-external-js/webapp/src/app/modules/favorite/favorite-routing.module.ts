import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserRouteAccessGuard } from '@app/core/guard';
import { UserResolver } from '@app/core/resolver';
import { USER } from '@app/core/service';
import { FavoriteTreeComponent } from '@app/modules/favorite/favorite-tree.component';
import { FavoritesResolver } from './favorite.resolver';

export const FAVORITE_ROUTES: Routes = [
    {
        path: '',
        component: FavoriteTreeComponent,
        data: {
            roles: USER,
            pageTitle: 'favorite.name',
        },
        resolve: {
            user: UserResolver,
            favorites: FavoritesResolver,
        },
        canActivate: [UserRouteAccessGuard],
    },
];

@NgModule({
    imports: [RouterModule.forChild(FAVORITE_ROUTES)],
    exports: [RouterModule],
    providers: [FavoritesResolver],
})
export class FavoriteRoutingModule {}
