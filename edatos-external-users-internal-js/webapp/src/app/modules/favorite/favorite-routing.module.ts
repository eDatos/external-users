import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ITEMS_PER_PAGE } from '@app/app.constants';
import { UserRouteAccessGuard } from '@app/core/guard';
import { FAVORITE_ROLES } from '@app/core/service';
import { FavoriteComponent } from '@app/modules/favorite/favorite.component';
import { PagingParamsResolver } from 'arte-ng/services';

export const FAVORITE_ROUTES: Routes = [
    {
        path: '',
        component: FavoriteComponent,
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
];

@NgModule({
    imports: [RouterModule.forChild(FAVORITE_ROUTES)],
    exports: [RouterModule],
})
export class FavoriteRoutingModule {}
