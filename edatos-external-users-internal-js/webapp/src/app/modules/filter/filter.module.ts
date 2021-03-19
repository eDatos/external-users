import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FilterListComponent } from '@app/modules/filter/filter-list/filter-list.component';
import { FilterRoutingModule } from '@app/modules/filter/filter-routing.module';
import { FilterFilter } from '@app/modules/filter/filter-search/filter-search';
import { SharedModule } from '@app/shared';
import { ArteEntityListEmptyModule, ArteSpinnerModule, ArteTableModule } from 'arte-ng';
import { ArteDirectivesModule } from 'arte-ng/directives';

@NgModule({
    declarations: [FilterListComponent],
    imports: [CommonModule, FilterRoutingModule, ArteSpinnerModule, ArteEntityListEmptyModule, ArteTableModule, SharedModule, ArteDirectivesModule],
    providers: [FilterFilter],
})
export class FilterModule {}
