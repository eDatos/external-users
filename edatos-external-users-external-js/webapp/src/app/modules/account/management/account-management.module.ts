import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedModule } from '@app/shared';
import { AccountManagementRoutingModule } from './account-management-routing.module';
import { AccountManagementComponent } from './account-management.component';
@NgModule({
    imports: [SharedModule, AccountManagementRoutingModule],
    declarations: [AccountManagementComponent],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AccountManagementModule {}
