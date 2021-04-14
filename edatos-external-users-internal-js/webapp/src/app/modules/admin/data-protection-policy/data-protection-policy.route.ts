import { Route, Routes } from '@angular/router';

import { DataProtectionPolicyComponent } from './data-protection-policy.component';

export const dataProtectionPolicyConfigurationRoutes: Routes = [
    {
        path: '',
        component: DataProtectionPolicyComponent
    },
    {
        path: 'edit',
        component: DataProtectionPolicyComponent
    },
];