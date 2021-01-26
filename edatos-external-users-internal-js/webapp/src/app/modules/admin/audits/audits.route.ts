import { Route } from '@angular/router';
import { ITEMS_PER_PAGE } from '@app/app.constants';
import { AuditsComponent } from './audits.component';
import { HERRAMIENTAS_ROLES } from '@app/core/service/auth';
import { UserRouteAccessGuard } from '@app/core/guard/user-route-access.guard';
import { PagingParamsResolver } from 'arte-ng/src/lib/services';


export const auditsRoute: Route = {
    path: 'audits',
    component: AuditsComponent,
    resolve: {
        pagingParams: PagingParamsResolver
    },
    data: {
        pageTitle: 'audits.title',
        roles: HERRAMIENTAS_ROLES,
        defaultPagingParams: {
            page: '1',
            sort: 'auditEventDate,desc',
            size: ITEMS_PER_PAGE
        }
    },
    canActivate: [UserRouteAccessGuard]
};