import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedModule } from '@app/shared';

import {
    AuditsComponent,
    LogsComponent,
    MetricsMonitoringModalComponent,
    MetricsMonitoringComponent,
    HealthModalComponent,
    HealthCheckComponent,
    ConfigurationComponent,
    AuditsService,
    ConfigurationService,
    HealthService,
    MetricsService,
    LogsService,
    AdminRoutingModule,
} from '.';

@NgModule({
    imports: [SharedModule, AdminRoutingModule],
    declarations: [AuditsComponent, LogsComponent, ConfigurationComponent, HealthCheckComponent, HealthModalComponent, MetricsMonitoringComponent, MetricsMonitoringModalComponent],
    entryComponents: [HealthModalComponent, MetricsMonitoringModalComponent],
    providers: [AuditsService, ConfigurationService, HealthService, MetricsService, LogsService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AdminModule {}
