import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ITEMS_PER_PAGE } from '@app/app.constants';
import { UserRouteAccessGuard } from '@app/core/guard';
import { FAVORITE_ROLES } from '@app/core/service';
import { FavoriteFormComponent } from '@app/modules/favorite/favorite-form/favorite-form.component';
import { FavoriteListComponent } from '@app/modules/favorite/favorite-list/favorite-list.component';
import { FavoriteResolver } from '@app/modules/favorite/favorite.resolver';
import { PagingParamsResolver } from 'arte-ng/services';

export const FAVORITE_ROUTES: Routes = [
    {
        path: '',
        component: FavoriteListComponent,
        resolve: {
            pagingParams: PagingParamsResolver,
        },
        data: {
            roles: FAVORITE_ROLES,
            pageTitle: 'favorite.home.title',
            defaultPagingParams: {
                page: '1',
                sort: 'created_date,desc',
                size: ITEMS_PER_PAGE,
            },
        },
        canActivate: [UserRouteAccessGuard],
    },
    {
        path: 'new',
        component: FavoriteFormComponent,
        data: {
            roles: FAVORITE_ROLES,
            pageTitle: 'favorite.form.new',
        },
        canActivate: [UserRouteAccessGuard],
    },
    {
        path: ':id',
        component: FavoriteFormComponent,
        resolve: {
            favorite: FavoriteResolver,
        },
        data: {
            roles: FAVORITE_ROLES,
            pageTitle: 'favorite.name',
        },
        canActivate: [UserRouteAccessGuard],
    },
];

@NgModule({
    imports: [RouterModule.forChild(FAVORITE_ROUTES)],
    exports: [RouterModule],
})
export class FavoriteRoutingModule {}
