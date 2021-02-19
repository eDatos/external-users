import { NgModule } from "@angular/core";
import { RouterModule, Routes } from '@angular/router';
import { HERRAMIENTAS_ROLES, ALL_ALLOWED } from './core/service';
import { UserRouteAccessGuard } from './core/guard';
import { DEFAULT_PATH } from './app.constants';

const APP_ROUTES: Routes = [
    {
        path: '',
        pathMatch: 'full',
        redirectTo: DEFAULT_PATH
    },
    {
        path: 'account',
        loadChildren: () => import('./modules/account/account.module').then(m => m.AccountModule),
        canLoad: [UserRouteAccessGuard]
    },
    {
        path: 'admin',
        loadChildren: () => import('./modules/admin/admin.module').then(m => m.AdminModule),
        canLoad: [UserRouteAccessGuard],
        data: {
            roles: HERRAMIENTAS_ROLES
        }
    },
    {
        path: 'hello',
        loadChildren: () => import('./modules/hello/hello.module').then(m => m.HelloModule),
        canLoad: [UserRouteAccessGuard],
        data: {
            roles: ALL_ALLOWED,
        }
    },
    {
        path: '**',
        redirectTo: 'notfound'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(APP_ROUTES, { useHash: true })],
    exports: [RouterModule]
})
export class AppRoutingModule {}