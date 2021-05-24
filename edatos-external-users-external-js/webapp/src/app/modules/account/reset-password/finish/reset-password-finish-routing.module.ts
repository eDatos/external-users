import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResetPasswordFinishComponent } from './reset-password-finish.component';

export const passwordResetFinishRoutes: Routes = [
    {
        path: '',
        component: ResetPasswordFinishComponent,
        data: {
            authorities: [],
            pageTitle: 'passwordReset.finish.home.title',
        },
    },
];

@NgModule({
    imports: [RouterModule.forChild(passwordResetFinishRoutes)],
    exports: [RouterModule],
})
export class PasswordResetFinishRoutingModule {}
