import { Routes, RouterModule } from '@angular/router';
import { auditsRoute } from './audits/audits.route';
import { configurationRoute } from './configuration/configuration.route';
import { logsRoute } from './logs/logs.route';
import { NgModule } from '@angular/core';

export const adminRoutes: Routes = [auditsRoute, configurationRoute, logsRoute];

@NgModule({
    imports: [RouterModule.forChild(adminRoutes)],
    exports: [RouterModule],
})
export class AdminRoutingModule {}
