import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FavoriteRoutingModule } from '@app/modules/favorite/favorite-routing.module';
import { SharedModule } from '@app/shared';
import { ArteAutocompleteModule, ArteEntityListEmptyModule, ArteSpinnerModule, ArteTableModule } from 'arte-ng';
import { ArteDirectivesModule } from 'arte-ng/directives';
import { FavoriteTreeComponent } from './favorite-tree.component';

@NgModule({
    declarations: [FavoriteTreeComponent],
    imports: [SharedModule, FavoriteRoutingModule, ArteDirectivesModule, CommonModule, ArteAutocompleteModule, ArteSpinnerModule, ArteEntityListEmptyModule, ArteTableModule],
})
export class FavoriteModule {}
