import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedModule } from '@app/shared';
import { DataProtectionPolicyRoutingModule } from './data-protection-policy-routing.module';
import { DataProtectionPolicyComponent } from './data-protection-policy.component';
import { DataProtectionPolicyService } from './data-protection-policy.service';

@NgModule({
    imports: [SharedModule, DataProtectionPolicyRoutingModule],
    declarations: [DataProtectionPolicyComponent],
    providers: [DataProtectionPolicyService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class DataProtectionPolicyModule {}
