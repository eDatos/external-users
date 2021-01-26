import { Route } from '@angular/router';

import { LogsComponent } from './logs.component';
import { HERRAMIENTAS_ROLES } from '@app/core/service/auth';
import { UserRouteAccessGuard } from '@app/core/guard/user-route-access.guard';

export const logsRoute: Route = {
    path: 'logs',
    component: LogsComponent,
    data: {
        pageTitle: 'logs.title',
        roles: HERRAMIENTAS_ROLES
    },
    canActivate: [UserRouteAccessGuard]
};
