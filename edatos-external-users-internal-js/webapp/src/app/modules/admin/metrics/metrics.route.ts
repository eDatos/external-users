import { Route } from '@angular/router';

import { MetricsMonitoringComponent } from './metrics.component';
import { HERRAMIENTAS_ROLES } from '@app/core/service/auth';
import { UserRouteAccessGuard } from '@app/core/guard/user-route-access.guard';

export const metricsRoute: Route = {
    path: 'metrics',
    component: MetricsMonitoringComponent,
    data: {
        pageTitle: 'metrics.title',
        roles: HERRAMIENTAS_ROLES,
    },
    canActivate: [UserRouteAccessGuard],
};
