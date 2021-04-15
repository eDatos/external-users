import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Language, Treatment } from '@app/core/model';
import { AccountUserService } from '@app/core/service/user';

@Component({
    selector: 'ac-account-management',
    templateUrl: './account-management.component.html',
})
export class AccountManagementComponent implements OnInit {
    error: string;
    success: string;
    public isSaving: Boolean;
    public account: any;

    public languageEnum = Language;
    public treatmentEnum = Treatment;

    constructor(private userService: AccountUserService, private route: ActivatedRoute, private router: Router) {}

    ngOnInit() {
        this.isSaving = false;
        this.userService
            .getLogueado()
            .toPromise()
            .then((account) => {
                this.account = account;
            });
    }

    save() {
        this.isSaving = true;
        this.userService.update(this.account).subscribe(
            () => {
                this.error = null;
                this.success = 'OK';
                this.isSaving = false;
                this.router.navigate(['account-management']);
            },
            () => {
                this.success = null;
                this.error = 'ERROR';
                this.isSaving = false;
            }
        );
    }

    clear() {
        const returnPath = ['account-management'];
        this.router.navigate(returnPath);
    }

    isEditMode(): Boolean {
        if (this.route.snapshot.url.length == 0) {
            return false;
        }
        const lastPath = this.route.snapshot.url[this.route.snapshot.url.length - 1].path;
        return lastPath === 'edit';
    }

    public navigateToChangePassword() {
        this.router.navigate(['account-management/change-password']);
    }

    public navigateToDataProtectionPolicy() {
        this.router.navigate(['data-protection-policy']);
    }
}
