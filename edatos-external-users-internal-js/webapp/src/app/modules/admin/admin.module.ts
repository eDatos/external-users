import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { ExternalUserService } from '@app/core/service';

import { SharedModule } from '@app/shared';
import { ArteAuditInfoModule, ArteAutocompleteModule, ArteCalendarModule, ArteSideMenuModule, ArteTriInputSwitchModule } from 'arte-ng';
import { ArteDirectivesModule } from 'arte-ng/directives';

import {
    AdminRoutingModule,
    AuditsComponent,
    AuditsService,
    ConfigurationComponent,
    ConfigurationService,
    ExternalUserDeleteDialogComponent,
    ExternalUserFormComponent,
    ExternalUserListComponent,
    ExternalUserSearchComponent,
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
    declarations: [
        AuditsComponent,
        ExternalUserSearchComponent,
        ExternalUserListComponent,
        ExternalUserFormComponent,
        ExternalUserDeleteDialogComponent,
        LogsComponent,
        ConfigurationComponent,
        HealthCheckComponent,
        HealthModalComponent,
        MetricsMonitoringComponent,
        MetricsMonitoringModalComponent,
    ],
    entryComponents: [
        ExternalUserFormComponent,
        ExternalUserDeleteDialogComponent,
        HealthModalComponent,
        MetricsMonitoringModalComponent
    ],
    providers: [AuditsService, ConfigurationService, HealthService, MetricsService, LogsService, ExternalUserService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AdminModule {
}
