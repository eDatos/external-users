import { Routes, RouterModule } from '@angular/router';

import { AccountManagementComponent } from './account-management.component';
import { UserRouteAccessGuard } from '@app/core/guard/user-route-access.guard';
import { NgModule } from '@angular/core';
import { Role } from '@app/core/model';

export const accountManagementRoutes: Routes = [
    {
        path: '',
        component: AccountManagementComponent,
        data: {
            pageTitle: 'account.title',
            roles: [Role.USER],
        },
        canActivate: [UserRouteAccessGuard],
    },
    {
        path: 'edit',
        component: AccountManagementComponent,
        data: {
            pageTitle: 'account.title',
            roles: [Role.USER],
        },
        canActivate: [UserRouteAccessGuard],
    },
];

@NgModule({
    imports: [RouterModule.forChild(accountManagementRoutes)],
    exports: [RouterModule],
})
export class AccountManagementRoutingModule {}
