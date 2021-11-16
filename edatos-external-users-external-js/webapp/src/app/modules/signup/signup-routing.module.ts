import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignupFormComponent } from './signup.component';
import { ALL_ALLOWED } from '@app/core/service/auth';

export const signupRoutes: Routes = [
    {
        path: '',
        component: SignupFormComponent,
        data: {
            roles: ALL_ALLOWED,
            pageTitle: 'signup.home.title',
        },
    },
];

@NgModule({
    imports: [RouterModule.forChild(signupRoutes)],
    exports: [RouterModule],
})
export class SignupRoutingModule {}
