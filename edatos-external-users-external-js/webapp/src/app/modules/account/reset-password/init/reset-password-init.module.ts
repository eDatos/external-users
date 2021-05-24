import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ResetPasswordService } from '@app/core/service';
import { SharedModule } from '@app/shared';
import { PasswordResetInitRoutingModule } from './reset-password-init-routing.module';
import { ResetPasswordInitComponent } from './reset-password-init.component';

@NgModule({
    imports: [SharedModule, PasswordResetInitRoutingModule],
    declarations: [ResetPasswordInitComponent],
    providers: [ResetPasswordService],
    entryComponents: [ResetPasswordInitComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ResetPasswordInitModule {}
