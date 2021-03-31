import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountUserService, Principal } from '@app/core/service';
import { User, UserAccountChangePassword } from '@app/core/model';
import { Router } from '@angular/router';

@Component({
    selector: 'change-password',
    templateUrl: './change-password.component.html',
})
export class ChangePasswordComponent implements OnInit {
    public isSaving: boolean = false;

    public account: any;
    public currentPassword: string;
    public newPassword: string;
    public confirmNewPassword: string;

    constructor(private userService: AccountUserService, private principal: Principal, private router: Router) {}

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
    }

    clear(): void {
        const returnPath = ['account-management'];
        this.router.navigate(returnPath);
    }

    save() {
        if (this.newPassword !== this.confirmNewPassword) {
            return;
        }
        this.isSaving = true;
        const accountChangePassword: UserAccountChangePassword = { currentPassword: this.currentPassword, newPassword: this.newPassword };
        this.userService.changeCurrentUserPassword(accountChangePassword).subscribe(
            (res) => this.onSaveSuccess(res),
            (error) => this.onSaveError(error)
        );
    }

    private onSaveSuccess(result: User): void {
        this.isSaving = false;
    }

    private onSaveError(error: any): void {
        this.isSaving = false;
    }
}
