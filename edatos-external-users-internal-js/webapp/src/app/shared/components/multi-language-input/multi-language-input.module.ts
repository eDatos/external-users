import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MultiLanguageInputComponent } from '@app/shared/components/multi-language-input/multi-language-input.component';
import { TranslateModule } from '@ngx-translate/core';
import { ArteInputTextModule } from 'arte-ng';

@NgModule({
    declarations: [MultiLanguageInputComponent],
    imports: [CommonModule, TranslateModule, FormsModule, ArteInputTextModule],
    exports: [MultiLanguageInputComponent],
})
export class MultiLanguageInputModule {}
