import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Issues, WorkplaceEnum } from '@app/core/model';
import { IssuesService } from '@app/core/service/issues';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'app-issues-form',
    templateUrl: './issues-form.component.html',
})
export class IssuesFormComponent implements OnInit {
    public issues: Issues = new Issues();
    public account: any;
    public isSaving: Boolean = false;
    public workplaceEnum = WorkplaceEnum;

    constructor(
        private issuesService: IssuesService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private messageService: MessageService,
        private translateService: TranslateService
    ) {}

    public ngOnInit() {
        this.account = this.activatedRoute.snapshot.data.user;
        this.issues.name = this.account.name;
        this.issues.surname1 = this.account.surname1;
        this.issues.surname2 = this.account.surname2;
        this.issues.email = this.account.email;
    }

    public submit(): void {
        this.isSaving = true;
        this.issuesService.create(this.issues).subscribe(
            (response) => this.onSaveSuccess(response),
            () => this.onSaveError()
        );
    }

    onSaveSuccess(response) {
        this.isSaving = false;
        this.messageService.add({
            key: 'customAlertKey',
            severity: 'success',
            summary: this.translateService.instant('issues.messages.onSuccessSumary'),
            detail: this.translateService.instant('issues.messages.onSuccess'),
            life: 5000,
        });
        this.navigateGoBack();
    }

    onSaveError() {
        this.isSaving = false;
        this.messageService.add({
            key: 'customAlertKey',
            severity: 'error',
            summary: this.translateService.instant('issues.messages.onFailSumary'),
            detail: this.translateService.instant('issues.messages.onFail'),
            life: 5000,
        });
    }

    navigateGoBack() {
        const returnPath = ['filter'];
        this.router.navigate(returnPath);
    }
}
