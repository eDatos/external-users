import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { ArteSpinnerModule } from "arte-ng";
import { ArteDirectivesModule } from "arte-ng/directives";
import { FamilySearchComponent } from './family-search';
import { FamilyComponent, FamilyDeleteDialogComponent, FamilyFormComponent, FamilyResolve, FamilyRoutingModule } from '.';

@NgModule({
    imports: [SharedModule, FamilyRoutingModule, ArteDirectivesModule, ArteSpinnerModule],
    declarations: [FamilyComponent, FamilyFormComponent, FamilyDeleteDialogComponent, FamilySearchComponent],
    entryComponents: [FamilyComponent, FamilyFormComponent, FamilyDeleteDialogComponent, FamilySearchComponent],
    providers: [FamilyResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FamilyModule {}
