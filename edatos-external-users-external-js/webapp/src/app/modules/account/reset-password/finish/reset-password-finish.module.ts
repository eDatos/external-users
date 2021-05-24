import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ResetPasswordService } from '@app/core/service';
import { SharedModule } from '@app/shared';
import { PasswordResetFinishRoutingModule } from './reset-password-finish-routing.module';
import { ResetPasswordFinishComponent } from './reset-password-finish.component';

@NgModule({
    imports: [SharedModule, PasswordResetFinishRoutingModule],
    declarations: [ResetPasswordFinishComponent],
    providers: [ResetPasswordService],
    entryComponents: [ResetPasswordFinishComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ResetPasswordFinishModule {}
