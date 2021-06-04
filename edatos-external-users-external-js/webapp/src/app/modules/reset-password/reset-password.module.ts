import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ResetPasswordService } from '@app/core/service';
import { SharedModule } from '@app/shared';
import { ResetPasswordFinishComponent } from './finish/reset-password-finish.component';
import { ResetPasswordInitComponent } from './init/reset-password-init.component';
import { PasswordResetInitRoutingModule } from './reset-password-routing.module';

@NgModule({
    imports: [SharedModule, PasswordResetInitRoutingModule],
    declarations: [ResetPasswordInitComponent, ResetPasswordFinishComponent],
    providers: [ResetPasswordService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ResetPasswordModule {}
