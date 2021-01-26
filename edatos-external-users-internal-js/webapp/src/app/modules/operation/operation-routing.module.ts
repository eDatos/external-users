import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OperationFormComponent } from './operation-form.component';
import { OperationComponent } from './operation.component';
import { OperationResolve } from './operation.resolve';
import { OPERATION_ROLES } from '@app/core/service/auth';
import { UserRouteAccessGuard } from '@app/core/guard/user-route-access.guard';
import { PagingParamsResolver } from 'arte-ng/src/lib/services';
import { ITEMS_PER_PAGE } from '@app/app.constants';

export const operationRoutes: Routes = [
    {
        path: '',
        component: OperationComponent,
        resolve: {
            pagingParams: PagingParamsResolver
        },
        data: {
            roles: OPERATION_ROLES,
            pageTitle: 'operation.home.title',
            defaultPagingParams: {
                page: '1',
                sort: 'code,asc',
                size: ITEMS_PER_PAGE
            }
        },
        canActivate: [UserRouteAccessGuard]
    },
    {
        path: 'new',
        component: OperationFormComponent,
        data: {
            roles: OPERATION_ROLES,
            pageTitle: 'operation.home.title'
        },
        canActivate: [UserRouteAccessGuard]
    },
    {
        path: ':id',
        component: OperationFormComponent,
        resolve: {
            operation: OperationResolve
        },
        data: {
            roles: OPERATION_ROLES,
            pageTitle: 'operation.home.title'
        },
        canActivate: [UserRouteAccessGuard]
    },
    {
        path: ':id/edit',
        component: OperationFormComponent,
        resolve: {
            operation: OperationResolve
        },
        data: {
            roles: OPERATION_ROLES,
            pageTitle: 'operation.home.title'
        },
        canActivate: [UserRouteAccessGuard]
    }
];

@NgModule({
    imports: [RouterModule.forChild(operationRoutes)],
    exports: [RouterModule]
})
export class OperationRoutingModule {}
