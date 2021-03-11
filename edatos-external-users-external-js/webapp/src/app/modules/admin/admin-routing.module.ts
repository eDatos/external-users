import { Routes, RouterModule } from '@angular/router';

import { UserRouteAccessGuard } from '@app/core/guard/user-route-access.guard';
import { auditsRoute } from './audits/audits.route';
import { configurationRoute } from './configuration/configuration.route';
import { healthRoute } from './health/health.route';
import { logsRoute } from './logs/logs.route';
import { metricsRoute } from './metrics/metrics.route';
import { NgModule } from '@angular/core';

export const adminRoutes: Routes = [auditsRoute, configurationRoute, healthRoute, logsRoute, metricsRoute];

@NgModule({
    imports: [RouterModule.forChild(adminRoutes)],
    exports: [RouterModule],
})
export class AdminRoutingModule {}
