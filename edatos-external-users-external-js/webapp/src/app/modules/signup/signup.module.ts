import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { SignupRoutingModule, SignupFormComponent } from '.';

@NgModule({
    imports: [SharedModule, SignupRoutingModule],
    declarations: [SignupFormComponent],
    entryComponents: [SignupFormComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class SignupModule {}
