import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { externalUsersRoutes } from './external-user-management/external-user.route';
import { auditsRoute } from './audits/audits.route';
import { configurationRoute } from './configuration/configuration.route';
import { healthRoute } from './health/health.route';
import { logsRoute } from './logs/logs.route';
import { metricsRoute } from './metrics/metrics.route';

export const adminRoutes: Routes = [auditsRoute, configurationRoute, healthRoute, logsRoute, ...externalUsersRoutes, metricsRoute];

@NgModule({
    imports: [RouterModule.forChild(adminRoutes)],
    exports: [RouterModule],
})
export class AdminRoutingModule {}
