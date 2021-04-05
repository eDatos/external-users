import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { StructuralResourcesTreeComponent } from '@app/shared/components/structural-resources-tree/structural-resources-tree.component';
import { TreeModule } from 'primeng/tree';

@NgModule({
    declarations: [StructuralResourcesTreeComponent],
    imports: [CommonModule, TreeModule],
    exports: [StructuralResourcesTreeComponent],
})
export class StructuralResourcesTreeModule {}
