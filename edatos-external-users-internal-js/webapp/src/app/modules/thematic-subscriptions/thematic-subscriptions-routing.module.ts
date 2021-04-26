import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserRouteAccessGuard } from '@app/core/guard';
import { ThematicSubscriptionsComponent } from '@app/modules/thematic-subscriptions/thematic-subscriptions.component';

export const THEMATIC_SUBSCRIPTIONS_ROUTES: Routes = [
    {
        path: '',
        pathMatch: 'full',
        component: ThematicSubscriptionsComponent,
        data: {
            pageTitle: 'thematicSubscriptions.home.title',
        },
        canActivate: [UserRouteAccessGuard],
    },
    {
        path: 'edit',
        component: ThematicSubscriptionsComponent,
        data: {
            pageTitle: 'thematicSubscriptions.home.title',
        },
        canActivate: [UserRouteAccessGuard],
    },
];

@NgModule({
    imports: [RouterModule.forChild(THEMATIC_SUBSCRIPTIONS_ROUTES)],
    exports: [RouterModule],
})
export class ThematicSubscriptionsRoutingModule {}
