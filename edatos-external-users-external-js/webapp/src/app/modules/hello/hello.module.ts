import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { HelloRoutingModule, HelloComponent } from '.';

@NgModule({
    imports: [SharedModule, HelloRoutingModule],
    declarations: [HelloComponent],
    entryComponents: [HelloComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HelloModule {}
