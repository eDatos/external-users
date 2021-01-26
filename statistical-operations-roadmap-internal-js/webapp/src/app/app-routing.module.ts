import { NgModule } from "@angular/core";
import { RouterModule, Routes } from '@angular/router';
import { HERRAMIENTAS_ROLES, FAMILY_ROLES, OPERATION_ROLES } from './core/service';
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
        path: 'family',
        loadChildren: () => import('./modules/family/family.module').then(m => m.FamilyModule),
        canLoad: [UserRouteAccessGuard],
        data: {
            roles: FAMILY_ROLES,
        }
    },
    {
        path: 'operation',
        loadChildren: () => import('./modules/operation/operation.module').then(m => m.OperationModule),
        canLoad: [UserRouteAccessGuard],
        data: {
            roles: OPERATION_ROLES,
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