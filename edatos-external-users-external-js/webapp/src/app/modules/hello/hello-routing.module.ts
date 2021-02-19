import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HelloComponent } from './hello.component';
import { ALL_ALLOWED } from '@app/core/service/auth';

export const helloRoutes: Routes = [
    {
        path: '',
        component: HelloComponent,
        data: {
            roles: ALL_ALLOWED,
            pageTitle: 'hello.home.title'
        }
    }
];

@NgModule({
    imports: [RouterModule.forChild(helloRoutes)],
    exports: [RouterModule]
})
export class HelloRoutingModule {}
