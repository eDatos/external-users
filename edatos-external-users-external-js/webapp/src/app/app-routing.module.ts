import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HERRAMIENTAS_ROLES, USER, ALL_ALLOWED } from './core/service';
import { UserRouteAccessGuard } from './core/guard';
import { DEFAULT_PATH } from './app.constants';

const APP_ROUTES: Routes = [
    {
        path: '',
        pathMatch: 'full',
        redirectTo: DEFAULT_PATH,
    },
    {
        path: 'account',
        loadChildren: () => import('./modules/account/register/account.module').then((m) => m.AccountModule),
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
        path: 'signup',
        loadChildren: () => import('./modules/signup/signup.module').then((m) => m.SignupModule),
        data: {
            roles: ALL_ALLOWED,
        },
    },
    {
        path: 'login',
        loadChildren: () => import('./modules/login/login.module').then((m) => m.LoginModule),
        data: {
            roles: ALL_ALLOWED,
        },
    },
    {
        path: 'account-management',
        loadChildren: () => import('./modules/account/management/account-management.module').then((m) => m.AccountManagementModule),
        canLoad: [UserRouteAccessGuard],
        data: {
            roles: USER,
        },
    },
    /*  {
        path: 'filter',
        loadChildren: () => import('./modules/filter/filter.module').then((m) => m.FilterModule),
        canLoad: [UserRouteAccessGuard],
        data: {
            roles: USER,
        },
    },
    {
        path: 'favorite',
        loadChildren: () => import('./modules/favorite/favorite.module').then((m) => m.FavoriteModule),
        canLoad: [UserRouteAccessGuard],
        data: {
            roles: USER,
        },
    },*/
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
