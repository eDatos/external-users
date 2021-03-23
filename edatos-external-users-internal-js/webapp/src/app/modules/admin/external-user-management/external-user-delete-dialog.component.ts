import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from '@app/core/model/user.model';
import { UserService } from '@app/core/service/user';
import { ArteEventManager } from 'arte-ng/services';

@Component({
    selector: 'app-external-user-delete-dialog',
    templateUrl: './external-user-delete-dialog.component.html',
})
export class ExternalUserDeleteDialogComponent {
    public static EVENT_NAME = 'UserModified';

    user: User;

    constructor(private userService: UserService, public activeModal: NgbActiveModal, private eventManager: ArteEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmAction(login) {
        if (this.user.login === login) {
            if (this.user.deletionDate) {
                this.confirmRestore(login);
            } else {
                this.confirmDelete(login);
            }
        }
    }

    private confirmDelete(login) {
        this.userService.delete(login).subscribe((response) => {
            this.eventManager.broadcast({
                name: ExternalUserDeleteDialogComponent.EVENT_NAME,
                content: response,
            });
            this.activeModal.dismiss('deleted');
        });
    }

    private confirmRestore(login) {
        this.userService.restore(login).subscribe((response) => {
            this.eventManager.broadcast({
                name: ExternalUserDeleteDialogComponent.EVENT_NAME,
                content: response,
            });
            this.activeModal.dismiss('restored');
        });
    }
}
