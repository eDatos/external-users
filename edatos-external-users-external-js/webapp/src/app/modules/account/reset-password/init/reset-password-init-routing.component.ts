import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResetPasswordInitComponent } from './reset-password-init.component';

export const passwordResetInitRoutes: Routes = [
    {
        path: 'reset-password',
        component: ResetPasswordInitComponent,
        data: {
            authorities: [],
            pageTitle: 'passwordReset.init.home.title',
        },
        //  canActivate: [UserPasswordNotLoggedInGuard],
    },
];

@NgModule({
    imports: [RouterModule.forChild(passwordResetInitRoutes)],
    exports: [RouterModule],
})
export class PasswordResetInitRoutingModule {}
