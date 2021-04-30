import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MultiLanguageInputModule } from '@app/shared/components/multi-language-input/multi-language-input.module';
import { StructuralResourcesTreeComponent } from '@app/shared/components/structural-resources-tree/structural-resources-tree.component';
import { TranslateModule } from '@ngx-translate/core';
import { ArteDirectivesModule } from 'arte-ng/directives';
import { TreeDragDropService } from 'primeng/api';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { TreeModule } from 'primeng/tree';

@NgModule({
    declarations: [StructuralResourcesTreeComponent],
    imports: [CommonModule, TreeModule, TranslateModule, FormsModule, InputTextModule, ArteDirectivesModule, DialogModule, MultiLanguageInputModule],
    exports: [StructuralResourcesTreeComponent],
    providers: [TreeDragDropService],
})
export class StructuralResourcesTreeModule {}
