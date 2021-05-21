import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IssuesFormComponent } from '@app/modules/issues/issues-form/issues-form.component';
import { USER } from '@app/core/service';
import { UserRouteAccessGuard } from '@app/core/guard';
import { UserResolver } from '@app/core/resolver';

export const ISSUES_ROUTE: Routes = [
    {
        path: '',
        component: IssuesFormComponent,
        resolve: {
            user: UserResolver,
        },
        data: {
            roles: USER,
            pageTitle: 'issues.home.title',
        },
        canActivate: [UserRouteAccessGuard],
    },
];

@NgModule({
    imports: [RouterModule.forChild(ISSUES_ROUTE)],
    exports: [RouterModule],
})
export class IssuesRoutingModule {}
