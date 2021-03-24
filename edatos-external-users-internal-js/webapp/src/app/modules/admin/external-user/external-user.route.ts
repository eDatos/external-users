import { Routes } from '@angular/router';
import { ExternalUserFormComponent } from '@app/modules/admin/external-user/external-user-form/external-user-form.component';
import { ExternalUserListComponent } from '@app/modules/admin/external-user/external-user-list/external-user-list.component';
import { ITEMS_PER_PAGE } from '@app/app.constants';
import { UserRouteAccessGuard } from '@app/core/guard/user-route-access.guard';
import { USER_MANAGEMENT_ROLES } from '@app/core/service/auth';
import { PagingParamsResolver } from 'arte-ng/services';

export const externalUsersRoutes: Routes = [
    {
        path: 'external-users',
        canActivate: [UserRouteAccessGuard],
        component: ExternalUserListComponent,
        resolve: {
            pagingParams: PagingParamsResolver,
        },
        data: {
            pageTitle: 'externalUserManagement.home.title',
            roles: USER_MANAGEMENT_ROLES,
            defaultPagingParams: {
                page: '1',
                sort: 'name,asc',
                size: ITEMS_PER_PAGE,
            },
        },
    },
    {
        path: 'external-users/:id',
        canActivate: [UserRouteAccessGuard],
        component: ExternalUserFormComponent,
        data: {
            pageTitle: 'externalUserManagement.home.title',
            roles: USER_MANAGEMENT_ROLES,
        },
    },
    {
        path: 'external-users/new',
        canActivate: [UserRouteAccessGuard],
        component: ExternalUserFormComponent,
        data: {
            pageTitle: 'externalUserManagement.home.title',
            roles: USER_MANAGEMENT_ROLES,
        },
    },
    {
        path: 'external-users/:id/edit',
        canActivate: [UserRouteAccessGuard],
        component: ExternalUserFormComponent,
        data: {
            roles: USER_MANAGEMENT_ROLES,
            pageTitle: 'externalUserManagement.home.title',
        },
    },
];
