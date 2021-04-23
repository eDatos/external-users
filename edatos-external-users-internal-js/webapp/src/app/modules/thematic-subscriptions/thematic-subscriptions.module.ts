import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ThematicSubscriptionsRoutingModule } from '@app/modules/thematic-subscriptions/thematic-subscriptions-routing.module';
import { ThematicSubscriptionsComponent } from '@app/modules/thematic-subscriptions/thematic-subscriptions.component';
import { SharedModule } from '@app/shared';

@NgModule({
    declarations: [ThematicSubscriptionsComponent],
    imports: [CommonModule, ThematicSubscriptionsRoutingModule, SharedModule],
})
export class ThematicSubscriptionsModule {}
