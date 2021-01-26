import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Operation, OperationService } from '@app/shared';

@Component({
    selector: 'app-operation-delete-dialog',
    templateUrl: './operation-delete-dialog.component.html'
})
export class OperationDeleteDialogComponent {
    operation: Operation;

    constructor(private operationService: OperationService, public activeModal: NgbActiveModal) { }

    clear() {
        this.activeModal.dismiss(false);
    }

    confirmDeletion(operation: Operation) {
        this.operationService.delete(operation.id).subscribe((response) => {
            this.operation = response;
            this.activeModal.dismiss(true);
        });
    }
}
