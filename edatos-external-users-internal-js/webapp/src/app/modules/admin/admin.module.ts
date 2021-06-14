import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { SharedModule } from '@app/shared';
import { ArteAuditInfoModule, ArteAutocompleteModule, ArteCalendarModule, ArteSideMenuModule, ArteSpinnerModule, ArteTriInputSwitchModule } from 'arte-ng';
import { ArteDirectivesModule } from 'arte-ng/directives';
import { InputSwitchModule } from 'primeng/inputswitch';

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
import { CategoryMasterTreeComponent } from './category-master-tree/category-master-tree.component';
import { DataProtectionPolicyComponent } from './data-protection-policy/data-protection-policy.component';
import { DataProtectionPolicyService } from './data-protection-policy/data-protection-policy.service';
import { ExternalOperationsListComponent } from './external-operations-list/external-operations-list.component';

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
        ArteSpinnerModule,
        InputSwitchModule,
    ],
    declarations: [
        AuditsComponent,
        LogsComponent,
        ConfigurationComponent,
        HealthCheckComponent,
        HealthModalComponent,
        MetricsMonitoringComponent,
        MetricsMonitoringModalComponent,
        DataProtectionPolicyComponent,
        CategoryMasterTreeComponent,
        ExternalOperationsListComponent,
    ],
    entryComponents: [HealthModalComponent, MetricsMonitoringModalComponent],
    providers: [AuditsService, ConfigurationService, HealthService, MetricsService, LogsService, DataProtectionPolicyService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AdminModule {}
