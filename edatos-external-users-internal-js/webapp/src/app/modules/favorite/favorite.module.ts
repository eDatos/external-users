import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FavoriteListComponent } from '@app/modules/favorite/favorite-list/favorite-list.component';
import { FavoriteRoutingModule } from '@app/modules/favorite/favorite-routing.module';
import { FavoriteFilter } from '@app/modules/favorite/favorite-search/favorite-search';
import { SharedModule } from '@app/shared';
import { ArteEntityListEmptyModule, ArteSpinnerModule, ArteTableModule } from 'arte-ng';
import { ArteDirectivesModule } from 'arte-ng/directives';
import { FavoriteFormComponent } from './favorite-form/favorite-form.component';

@NgModule({
    declarations: [FavoriteListComponent, FavoriteFormComponent],
    imports: [CommonModule, FavoriteRoutingModule, ArteSpinnerModule, ArteEntityListEmptyModule, ArteTableModule, SharedModule, ArteDirectivesModule],
    providers: [FavoriteFilter],
})
export class FavoriteModule {}
