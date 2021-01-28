import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedModule } from '@app/shared';

import {
    AuditsComponent,
    UserMgmtComponent,
    UserMgmtFormComponent,
    UserMgmtDeleteDialogComponent,
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
    AdminRoutingModule
} from '.';
import { UserSearchComponent } from './user-management/user-search';

@NgModule({
    imports: [
        SharedModule,
        AdminRoutingModule
    ],
    declarations: [
        AuditsComponent,
        UserSearchComponent,
        UserMgmtComponent,
        UserMgmtFormComponent,
        UserMgmtDeleteDialogComponent,
        LogsComponent,
        ConfigurationComponent,
        HealthCheckComponent,
        HealthModalComponent,
        MetricsMonitoringComponent,
        MetricsMonitoringModalComponent
    ],
    entryComponents: [UserMgmtFormComponent, UserMgmtDeleteDialogComponent, HealthModalComponent, MetricsMonitoringModalComponent],
    providers: [AuditsService, ConfigurationService, HealthService, MetricsService, LogsService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminModule {}
