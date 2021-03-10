import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { LoginRoutingModule, LoginComponent } from '.';

@NgModule({
    imports: [SharedModule, LoginRoutingModule],
    declarations: [LoginComponent],
    entryComponents: [LoginComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class LoginModule {}
