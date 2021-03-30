import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountUserService, Principal } from '@app/core/service';
import { User } from '@app/core/model';
import { Router } from '@angular/router';

@Component({
    selector: 'change-password',
    templateUrl: './change-password.component.html',
})
export class ChangePasswordComponent implements OnInit {
    public isSaving: boolean = false;

    public account: any;
    public password: string;
    public oldPassword: string;
    public confirmPassword: string;

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
        if (this.password !== this.confirmPassword) {
            return;
        }
        this.isSaving = true;
        this.userService.changeCurrentUserPassword(this.password).subscribe(
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
