import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { dataProtectionPolicyConfigurationRoute } from './data-protection-policy/data-protection-policy.route';
import { auditsRoute } from './audits/audits.route';
import { configurationRoute } from './configuration/configuration.route';
import { healthRoute } from './health/health.route';
import { logsRoute } from './logs/logs.route';
import { metricsRoute } from './metrics/metrics.route';

export const adminRoutes: Routes = [auditsRoute, configurationRoute, healthRoute, logsRoute, metricsRoute, {
    path: 'data-protection-policy',
    children: dataProtectionPolicyConfigurationRoute
}];

@NgModule({
    imports: [RouterModule.forChild(adminRoutes)],
    exports: [RouterModule],
})
export class AdminRoutingModule {}
