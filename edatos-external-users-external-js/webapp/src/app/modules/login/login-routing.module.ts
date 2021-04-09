import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login.component';
import { ALL_ALLOWED } from '@app/core/service/auth';
import { NotLoggedUserCanAccessGuard } from '@app/core/guard/not-logged-user-can-access.guard';

export const loginRoutes: Routes = [
    {
        path: '',
        component: LoginComponent,
        data: {
            pageTitle: 'login.home.title',
        },
        canActivate: [NotLoggedUserCanAccessGuard],
    },
];

@NgModule({
    imports: [RouterModule.forChild(loginRoutes)],
    exports: [RouterModule],
})
export class LoginRoutingModule {}
