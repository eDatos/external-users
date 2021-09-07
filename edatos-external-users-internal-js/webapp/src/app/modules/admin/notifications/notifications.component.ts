import { Component, OnDestroy, OnInit } from '@angular/core';
import { ExternalUser, Notification } from '@app/core/model';
import { ExternalUserService } from '@app/core/service';
import { NotificationService } from '@app/shared/service';
import { TranslateService } from '@ngx-translate/core';
import { ArteEventManager } from 'arte-ng/services';
import { MessageService } from 'primeng/api';
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-notifications',
    templateUrl: './notifications.component.html',
})
export class NotificationsComponent implements OnInit, OnDestroy {
    public subscription: Subscription;
    public isSaving: boolean;

    public externalUsers: ExternalUser[] = [];
    public notification: Notification;

    constructor(
        private eventManager: ArteEventManager,
        public notificationService: NotificationService,
        public externalUserService: ExternalUserService,
        public messageService: MessageService,
        public translateService: TranslateService
    ) {}

    public ngOnInit() {
        this.isSaving = false;
        this.searchExternalUsers();
    }

    public ngOnDestroy() {
        this.eventManager.destroy(this.subscription);
    }

    public send() {
        this.isSaving = true;
        this.notificationService.send(this.notification).subscribe(
            () => this.onSaveSuccess(),
            () => this.onSaveError()
        );
    }

    private searchExternalUsers() {
        this.externalUserService
            .find({
                page: 0,
                size: 100000000,
                sort: ['asc'],
            })
            .subscribe((results) => {
                this.externalUsers = results.body;
            });
    }

    private onSaveSuccess() {
        this.messageService.add({
            key: 'customAlertKey',
            severity: 'success',
            summary: this.translateService.instant('notifications.message.onSuccessSumary'),
            detail: this.translateService.instant('notifications.message.onSuccess'),
            life: 5000,
        });
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
