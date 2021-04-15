import { DatePipe, CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ArteAutocompleteModule, ArteEntityListEmptyModule, ArteInputModule, ArteOrderListModule, ArteSpinnerModule, ArteTableModule } from 'arte-ng';

import { GenericModalService } from 'arte-ng/services';
import { FormcontrolStatusService, ArteDirectivesModule } from 'arte-ng/directives';

import { NgbModule, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

import { TranslateModule } from '@ngx-translate/core';
import { CalendarModule } from 'primeng/calendar';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ChipsModule } from 'primeng/chips';
import { ButtonModule } from 'primeng/button';
import { SelectButtonModule } from 'primeng/selectbutton';
import { ListboxModule } from 'primeng/listbox';
import { OrderListModule } from 'primeng/orderlist';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputMaskModule } from 'primeng/inputmask';
import { FileUploadModule } from 'primeng/fileupload';
import { TableModule } from 'primeng/table';
import { TooltipModule } from 'primeng/tooltip';
import { EditorModule } from 'primeng/editor';

import { NgArrayPipesModule } from 'ngx-pipes';

import { PasswordMatchValidatorDirective } from './directives';
import { PasswordNotMatchValidatorDirective } from './directives';
import { MultiLanguageEditorComponent } from './components/multi-language-editor/multi-language-editor.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        ReactiveFormsModule,
        NgbModule,
        InfiniteScrollModule,
        CalendarModule,
        AutoCompleteModule,
        ChipsModule,
        ButtonModule,
        SelectButtonModule,
        ListboxModule,
        OrderListModule,
        CheckboxModule,
        InputTextareaModule,
        InputMaskModule,
        FileUploadModule,
        TranslateModule,
        TableModule,
        NgArrayPipesModule,
        FormsModule,
        TooltipModule,
        ArteInputModule,
        ArteTableModule,
        ArteOrderListModule,
        ArteAutocompleteModule,
        ArteSpinnerModule,
        ArteEntityListEmptyModule,
        ArteDirectivesModule,
        EditorModule
    ],
    declarations: [PasswordMatchValidatorDirective, PasswordNotMatchValidatorDirective, MultiLanguageEditorComponent],
    providers: [
        FormcontrolStatusService,
        DatePipe,
        {
            // INFRASTR-205 Way to avoid null injection of NgbModal in GenericModalService constructor
            provide: GenericModalService,
            useClass: GenericModalService,
            deps: [NgbModal],
        }, // Add at this point toa void No component factory found for <Component>. Did you add it to @NgModule.entryComponents?
    ],
    exports: [
        FormsModule,
        CommonModule,
        NgbModule,
        InfiniteScrollModule,
        AutoCompleteModule,
        ChipsModule,
        CalendarModule,
        ButtonModule,
        SelectButtonModule,
        ListboxModule,
        OrderListModule,
        CheckboxModule,
        InputTextareaModule,
        InputMaskModule,
        FileUploadModule,
        TranslateModule,
        TableModule,
        TooltipModule,
        NgArrayPipesModule,
        ReactiveFormsModule,
        ArteInputModule,
        ArteTableModule,
        ArteOrderListModule,
        ArteAutocompleteModule,
        ArteSpinnerModule,
        ArteEntityListEmptyModule,
        PasswordMatchValidatorDirective,
        PasswordNotMatchValidatorDirective,
        ArteDirectivesModule,
        MultiLanguageEditorComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class SharedModule {}
