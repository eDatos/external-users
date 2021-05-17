import { Component } from '@angular/core';
import { VERSION } from '@app/app.constants';
import { AccountUserService } from '@app/core/service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-about-dialog',
    templateUrl: './about-dialog.component.html',
})
export class AboutDialogComponent {
    public version: string;

    constructor(private activeModal: NgbActiveModal, private userService: AccountUserService) {
        this.version = VERSION ? 'v' + VERSION : '';
    }

    public clear() {
        this.activeModal.dismiss(false);
    }
}
