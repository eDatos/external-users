import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResetPasswordInitComponent } from './reset-password-init.component';

export const passwordResetInitRoutes: Routes = [
    {
        path: '',
        component: ResetPasswordInitComponent,
        data: {
            authorities: [],
            pageTitle: 'passwordReset.init.title',
        },
    },
];

@NgModule({
    imports: [RouterModule.forChild(passwordResetInitRoutes)],
    exports: [RouterModule],
})
export class PasswordResetInitRoutingModule {}
