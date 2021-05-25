import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ResetPasswordService } from '@app/core/service';

@Component({
    selector: 'app-reset-password-init',
    templateUrl: './reset-password-init.component.html',
    styleUrls: ['../reset-password.component.scss'],
})
export class ResetPasswordInitComponent implements OnInit {
    public email: string;

    constructor(private resetPasswordService: ResetPasswordService, private router: Router) {
        this.email = '';
    }

    ngOnInit() {}

    public requestReset() {
        this.resetPasswordService.resetPassword(this.email).subscribe((result) => {
            this.router.navigate(['filter']);
        });
    }
}
