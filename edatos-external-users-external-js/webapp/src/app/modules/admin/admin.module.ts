import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedModule } from '@app/shared';

import { AuditsComponent, LogsComponent, ConfigurationComponent, AuditsService, ConfigurationService, LogsService, AdminRoutingModule } from '.';

@NgModule({
    imports: [SharedModule, AdminRoutingModule],
    declarations: [AuditsComponent, LogsComponent, ConfigurationComponent],
    entryComponents: [],
    providers: [AuditsService, ConfigurationService, LogsService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AdminModule {}
