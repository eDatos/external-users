import { AfterViewInit, Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { KeyAndPassword } from '@app/core/model/key-poassword.model';
import { ResetPasswordService } from '@app/core/service';

@Component({
    selector: 'jhi-password-reset-finish',
    templateUrl: './reset-password-finish.component.html',
    styleUrls: ['../reset-password.component.scss'],
})
export class ResetPasswordFinishComponent implements OnInit {
    public isSaving: boolean;
    public confirmPassword: string;
    public keyAndPassword: KeyAndPassword;

    constructor(private resetPasswordService: ResetPasswordService, private router: Router, private route: ActivatedRoute, private elementRef: ElementRef) {
        this.isSaving = false;
        this.keyAndPassword = new KeyAndPassword();
    }

    ngOnInit() {
        this.route.queryParams.subscribe((params) => {
            this.keyAndPassword.key = params['key'];
        });
    }

    public save() {
        this.isSaving = true;
        this.resetPasswordService.changePasswordReset(this.keyAndPassword).subscribe((result) => {
            this.navigateToLogin();
        });
    }

    public navigateToLogin() {
        this.router.navigate(['login']);
    }
}
