import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FavoriteRoutingModule } from '@app/modules/favorite/favorite-routing.module';
import { FavoriteFilter } from '@app/modules/favorite/favorite-search/favorite-search';
import { FavoriteComponent } from '@app/modules/favorite/favorite.component';
import { SharedModule } from '@app/shared';
import { ArteEntityListEmptyModule, ArteSpinnerModule, ArteTableModule } from 'arte-ng';
import { ArteDirectivesModule } from 'arte-ng/directives';

@NgModule({
    declarations: [FavoriteComponent],
    imports: [CommonModule, FavoriteRoutingModule, ArteSpinnerModule, ArteEntityListEmptyModule, ArteTableModule, SharedModule, ArteDirectivesModule],
    providers: [FavoriteFilter],
})
export class FavoriteModule {}
