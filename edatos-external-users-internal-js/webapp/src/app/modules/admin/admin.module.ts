import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '@app/shared';
import { ArteAuditInfoModule, ArteAutocompleteModule, ArteCalendarModule, ArteSideMenuModule, ArteTriInputSwitchModule } from 'arte-ng';
import { ArteDirectivesModule } from 'arte-ng/directives';

import {
    AdminRoutingModule,
    AuditsComponent,
    AuditsService,
    ConfigurationComponent,
    ConfigurationService,
    HealthCheckComponent,
    HealthModalComponent,
    HealthService,
    LogsComponent,
    LogsService,
    MetricsMonitoringComponent,
    MetricsMonitoringModalComponent,
    MetricsService,
} from '.';

@NgModule({
    imports: [
        SharedModule,
        AdminRoutingModule,
        ArteCalendarModule,
        ArteSideMenuModule,
        ArteDirectivesModule,
        ArteAutocompleteModule,
        ArteAuditInfoModule,
        ArteTriInputSwitchModule,
    ],
    declarations: [AuditsComponent, LogsComponent, ConfigurationComponent, HealthCheckComponent, HealthModalComponent, MetricsMonitoringComponent, MetricsMonitoringModalComponent],
    entryComponents: [HealthModalComponent, MetricsMonitoringModalComponent],
    providers: [AuditsService, ConfigurationService, HealthService, MetricsService, LogsService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AdminModule {}
