
import { Routes } from '@angular/router';
import { UserMgmtComponent } from './user-management.component';
import { UserMgmtFormComponent } from './user-management-form.component';
import { ITEMS_PER_PAGE } from '@app/app.constants';
import { UserRouteAccessGuard } from '@app/core/guard/user-route-access.guard';
import { USER_MANAGEMENT_ROLES } from '@app/core/service/auth';
import { PagingParamsResolver } from 'arte-ng/src/lib/services';


export const userMgmtRoute: Routes = [
    {
        path: 'user-management',
        canActivate: [UserRouteAccessGuard],
        component: UserMgmtComponent,
        resolve: {
            pagingParams: PagingParamsResolver
        },
        data: {
            pageTitle: 'userManagement.home.title',
            roles: USER_MANAGEMENT_ROLES,
            defaultPagingParams: {
                page: '1',
                sort: 'login,asc',
                size: ITEMS_PER_PAGE
            }
        }
    },
    {
        path: 'user-management/:login',
        canActivate: [UserRouteAccessGuard],
        component: UserMgmtFormComponent,
        data: {
            pageTitle: 'userManagement.home.title',
            roles: USER_MANAGEMENT_ROLES
        }
    },
    {
        path: 'user-management-new',
        canActivate: [UserRouteAccessGuard],
        component: UserMgmtFormComponent,
        data: {
            pageTitle: 'userManagement.home.title',
            roles: USER_MANAGEMENT_ROLES
        }
    },
    {
        path: 'user-management/:login/edit',
        canActivate: [UserRouteAccessGuard],
        component: UserMgmtFormComponent,
        data: {
            roles: USER_MANAGEMENT_ROLES,
            pageTitle: 'userManagement.home.title'
        }
    }
];
