import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HealthModalComponent } from './health-modal.component';

import { HealthService } from './health.service';

@Component({
    selector: 'app-health',
    templateUrl: './health.component.html',
    styles: ['.table-responsive { margin-top: 1px; }'],
})
export class HealthCheckComponent implements OnInit {
    healthData: any;
    updatingHealth: boolean;

    constructor(private modalService: NgbModal, private healthService: HealthService) {}

    ngOnInit() {
        this.refresh();
    }

    baseName(name: string) {
        return this.healthService.getBaseName(name);
    }

    getBadgeClass(statusState) {
        if (statusState === 'UP') {
            return 'badge-success';
        } else {
            return 'badge-danger';
        }
    }

    refresh() {
        this.updatingHealth = true;

        this.healthService.checkHealth().subscribe(
            (health) => {
                this.healthData = this.healthService.transformHealthData(health);
                this.updatingHealth = false;
            },
            (error) => {
                if (error.status === 503) {
                    this.healthData = this.healthService.transformHealthData(error.json());
                    this.updatingHealth = false;
                }
            }
        );
    }

    showHealth(health: any) {
        const modalRef = this.modalService.open(HealthModalComponent);
        modalRef.componentInstance.currentHealth = health;
        modalRef.result.then(
            (result) => {
                // Left blank intentionally, nothing to do here
            },
            (reason) => {
                // Left blank intentionally, nothing to do here
            }
        );
    }

    subSystemName(name: string) {
        return this.healthService.getSubSystemName(name);
    }
}
