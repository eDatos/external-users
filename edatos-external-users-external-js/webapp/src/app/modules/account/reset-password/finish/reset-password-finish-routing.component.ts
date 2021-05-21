import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResetPasswordFinishComponent } from './reset-password-finish.component';

export const passwordResetFinishRoutes: Routes = [
    {
        path: 'portal/recuperar-contrasena/cambiar-contrasena',
        component: ResetPasswordFinishComponent,
        data: {
            authorities: [],
            pageTitle: 'passwordReset.finish.home.title',
        },
        //  canActivate: [UserPasswordNotLoggedInGuard, ResetKeyRouteAccessGuard],
    },
];

@NgModule({
    imports: [RouterModule.forChild(passwordResetFinishRoutes)],
    exports: [RouterModule],
})
export class PasswordResetFinishRoutingModule {}
