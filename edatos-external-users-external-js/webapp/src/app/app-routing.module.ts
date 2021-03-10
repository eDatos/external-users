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
        path: 'signup',
        loadChildren: () => import('./modules/signup/signup.module').then((m) => m.SignupModule),
        canLoad: [UserRouteAccessGuard],
        data: {
            roles: ALL_ALLOWED,
        },
    },
    {
        path: 'login',
        loadChildren: () => import('./modules/login/login.module').then((m) => m.LoginModule),
        canLoad: [UserRouteAccessGuard],
        data: {
            roles: ALL_ALLOWED,
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
