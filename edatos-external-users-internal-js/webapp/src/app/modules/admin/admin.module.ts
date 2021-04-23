import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '@app/shared';
import { ArteAuditInfoModule, ArteAutocompleteModule, ArteCalendarModule, ArteSideMenuModule, ArteTriInputSwitchModule } from 'arte-ng';
import { ArteDirectivesModule } from 'arte-ng/directives';
import { DataProtectionPolicyComponent } from './data-protection-policy/data-protection-policy.component';
import { DataProtectionPolicyService } from './data-protection-policy/data-protection-policy.service';

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
    declarations: [AuditsComponent, LogsComponent, ConfigurationComponent, HealthCheckComponent, HealthModalComponent, MetricsMonitoringComponent, MetricsMonitoringModalComponent, DataProtectionPolicyComponent],
    entryComponents: [HealthModalComponent, MetricsMonitoringModalComponent],
    providers: [AuditsService, ConfigurationService, HealthService, MetricsService, LogsService, DataProtectionPolicyService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AdminModule {}
