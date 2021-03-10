import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login.component';
import { ALL_ALLOWED } from '@app/core/service/auth';

export const loginRoutes: Routes = [
    {
        path: '',
        component: LoginComponent,
        data: {
            roles: ALL_ALLOWED,
            pageTitle: 'login.home.title',
        },
    },
];

@NgModule({
    imports: [RouterModule.forChild(loginRoutes)],
    exports: [RouterModule],
})
export class LoginRoutingModule {}
