import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DEFAULT_PATH } from './app.constants';
import { UserRouteAccessGuard } from './core/guard';
import { FILTER_ROLES, HERRAMIENTAS_ROLES, OPERATION_ROLES, FAVORITE_ROLES } from './core/service';

const APP_ROUTES: Routes = [
    {
        path: '',
        pathMatch: 'full',
        redirectTo: DEFAULT_PATH,
    },
    {
        path: 'account',
        loadChildren: () => import('./modules/account/account.module').then((m) => m.AccountModule),
        canLoad: [UserRouteAccessGuard],
    },
    {
        path: 'admin',
        loadChildren: () => import('./modules/admin/admin.module').then((m) => m.AdminModule),
        canLoad: [UserRouteAccessGuard],
        data: {
            roles: HERRAMIENTAS_ROLES,
        },
    },
    {
        path: 'filter',
        loadChildren: () => import('./modules/filter/filter.module').then((m) => m.FilterModule),
        canLoad: [UserRouteAccessGuard],
        data: {
            roles: FILTER_ROLES,
        },
    },
    {
        path: 'favorite',
        loadChildren: () => import('./modules/favorite/favorite.module').then((m) => m.FavoriteModule),
        canLoad: [UserRouteAccessGuard],
        data: {
            roles: FAVORITE_ROLES,
        },
    },
    {
        path: '**',
        redirectTo: 'notfound',
    },
];

@NgModule({
    imports: [RouterModule.forRoot(APP_ROUTES, { useHash: true })],
    exports: [RouterModule],
})
export class AppRoutingModule {}
