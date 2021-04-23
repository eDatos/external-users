import { Routes, RouterModule } from '@angular/router';

import { DataProtectionPolicyComponent } from './data-protection-policy.component';
import { NgModule } from '@angular/core';

export const accountManagementRoutes: Routes = [
    {
        path: '',
        component: DataProtectionPolicyComponent,
        data: {
            pageTitle: 'account.title'
        }
    }
];

@NgModule({
    imports: [RouterModule.forChild(accountManagementRoutes)],
    exports: [RouterModule],
})
export class DataProtectionPolicyRoutingModule {}
