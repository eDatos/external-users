import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ArteSpinnerModule, ArteTableModule } from 'arte-ng';
import { ArteDirectivesModule } from 'arte-ng/directives';
import { SharedModule } from '@app/shared';
import { IssuesFormComponent } from './issues-form/issues-form.component';
import { IssuesRoutingModule } from './issues-routing.module';

@NgModule({
    declarations: [IssuesFormComponent],
    imports: [CommonModule, IssuesRoutingModule, ArteSpinnerModule, ArteTableModule, SharedModule, ArteDirectivesModule],
    //providers: [FilterFilter],
})
export class IssuesModule {}