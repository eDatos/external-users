import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { StructuralResourcesTreeComponent } from '@app/shared/components/structural-resources-tree/structural-resources-tree.component';
import { TranslateModule } from '@ngx-translate/core';
import { TreeModule } from 'primeng/tree';
import { TreeDragDropService } from 'primeng/api';

@NgModule({
    declarations: [StructuralResourcesTreeComponent],
    imports: [CommonModule, TreeModule, TranslateModule, FormsModule],
    exports: [StructuralResourcesTreeComponent],
    providers: [TreeDragDropService],
})
export class StructuralResourcesTreeModule {}
