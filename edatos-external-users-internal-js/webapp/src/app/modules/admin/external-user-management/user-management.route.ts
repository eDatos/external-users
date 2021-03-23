import { Routes } from '@angular/router';
import { ExternalUserFormComponent } from '@app/modules/admin/external-user-management/external-user-form/external-user-form.component';
import { ExternalUserListComponent } from '@app/modules/admin/external-user-management/external-user-list/external-user-list.component';
import { ITEMS_PER_PAGE } from '@app/app.constants';
import { UserRouteAccessGuard } from '@app/core/guard/user-route-access.guard';
import { USER_MANAGEMENT_ROLES } from '@app/core/service/auth';
import { PagingParamsResolver } from 'arte-ng/services';

export const externalUsersManagementRoutes: Routes = [
    {
        path: 'user-management',
        canActivate: [UserRouteAccessGuard],
        component: ExternalUserListComponent,
        resolve: {
            pagingParams: PagingParamsResolver,
        },
        data: {
            pageTitle: 'userManagement.home.title',
            roles: USER_MANAGEMENT_ROLES,
            defaultPagingParams: {
                page: '1',
                sort: 'login,asc',
                size: ITEMS_PER_PAGE,
            },
        },
    },
    {
        path: 'user-management/:login',
        canActivate: [UserRouteAccessGuard],
        component: ExternalUserFormComponent,
        data: {
            pageTitle: 'userManagement.home.title',
            roles: USER_MANAGEMENT_ROLES,
        },
    },
    {
        path: 'user-management-new',
        canActivate: [UserRouteAccessGuard],
        component: ExternalUserFormComponent,
        data: {
            pageTitle: 'userManagement.home.title',
            roles: USER_MANAGEMENT_ROLES,
        },
    },
    {
        path: 'user-management/:login/edit',
        canActivate: [UserRouteAccessGuard],
        component: ExternalUserFormComponent,
        data: {
            roles: USER_MANAGEMENT_ROLES,
            pageTitle: 'userManagement.home.title',
        },
    },
];
