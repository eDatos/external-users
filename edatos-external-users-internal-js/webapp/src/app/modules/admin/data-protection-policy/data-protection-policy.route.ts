import { Route } from '@angular/router';

import { DataProtectionPolicyComponent } from './data-protection-policy.component';

export const dataProtectionPolicyConfigurationRoute: Route = {
    path: 'data-protection-policy',
    children: [
        {
            path: '',
            component: DataProtectionPolicyComponent
        },
        {
            path: 'edit',
            component: DataProtectionPolicyComponent
        }
    ]
}