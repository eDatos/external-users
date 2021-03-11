import { Route } from '@angular/router';

import { ConfigurationComponent } from './configuration.component';
import { HERRAMIENTAS_ROLES } from '@app/core/service/auth';
import { UserRouteAccessGuard } from '@app/core/guard/user-route-access.guard';

export const configurationRoute: Route = {
    path: 'configuration',
    component: ConfigurationComponent,
    data: {
        pageTitle: 'configuration.title',
        roles: HERRAMIENTAS_ROLES,
    },
    canActivate: [UserRouteAccessGuard],
};
