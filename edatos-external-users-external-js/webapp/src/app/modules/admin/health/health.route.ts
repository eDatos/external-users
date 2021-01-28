import { Route } from '@angular/router';

import { HealthCheckComponent } from './health.component';
import { HERRAMIENTAS_ROLES } from '@app/core/service/auth';
import { UserRouteAccessGuard } from '@app/core/guard/user-route-access.guard';

export const healthRoute: Route = {
    path: 'health',
    component: HealthCheckComponent,
    data: {
        pageTitle: 'health.title',
        roles: HERRAMIENTAS_ROLES
    },
    canActivate: [UserRouteAccessGuard]
};
