import { Routes, RouterModule } from '@angular/router';
import { UserRouteAccessGuard } from '@app/core/guard/user-route-access.guard';
import { NgModule } from '@angular/core';
import { Role } from '@app/core/model';
import { ChangePasswordComponent } from './change-password.component';

export const changePasswordRoutes: Routes = [
    {
        path: '',
        component: ChangePasswordComponent,
        data: {
            pageTitle: 'account.title',
            roles: [Role.USER],
        },
        canActivate: [UserRouteAccessGuard],
    },
];

@NgModule({
    imports: [RouterModule.forChild(changePasswordRoutes)],
    exports: [RouterModule],
})
export class ChangePasswordRoutingModule {}
