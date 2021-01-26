import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Family, FamilyService } from '@app/shared';

@Component({
    selector: 'app-family-delete-dialog',
    templateUrl: './family-delete-dialog.component.html'
})
export class FamilyDeleteDialogComponent {
    family: Family;

    constructor(private familyService: FamilyService, public activeModal: NgbActiveModal) { }

    clear() {
        this.activeModal.dismiss(false);
    }

    confirmDeletion(family: Family) {
        this.familyService.delete(family.id).subscribe((response) => {
            this.family = response;
            this.activeModal.dismiss(true);
        });
    }
}
