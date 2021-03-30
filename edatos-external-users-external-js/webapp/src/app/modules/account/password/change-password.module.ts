import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedModule } from '@app/shared';
import { ChangePasswordRoutingModule } from './change-password-routing.module';

import { ChangePasswordComponent } from './change-password.component';
@NgModule({
    imports: [SharedModule, ChangePasswordRoutingModule],
    declarations: [ChangePasswordComponent],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ChangePasswordModule {}
