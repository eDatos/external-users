import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { OperationSearchComponent } from './operation-search';
import { OperationComponent, OperationDeleteDialogComponent, OperationFormComponent, OperationResolve, OperationRoutingModule } from '.';

@NgModule({
    imports: [SharedModule, OperationRoutingModule],
    declarations: [OperationComponent, OperationFormComponent, OperationDeleteDialogComponent, OperationSearchComponent],
    entryComponents: [OperationComponent, OperationFormComponent, OperationDeleteDialogComponent, OperationSearchComponent],
    providers: [OperationResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OperationModule {}
