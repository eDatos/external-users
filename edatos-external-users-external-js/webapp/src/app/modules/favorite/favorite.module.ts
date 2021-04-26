import { NgModule } from '@angular/core';
import { FavoriteRoutingModule } from '@app/modules/favorite/favorite-routing.module';
import { SharedModule } from '@app/shared';
import { FavoriteTreeComponent } from './favorite-tree.component';

@NgModule({
    declarations: [FavoriteTreeComponent],
    imports: [SharedModule, FavoriteRoutingModule],
})
export class FavoriteModule {}
