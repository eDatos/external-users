import { AfterViewInit, Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { KeyAndPassword } from '@app/core/model/key-poassword.model';
import { ResetPasswordService } from '@app/core/service';

@Component({
    selector: 'jhi-password-reset-finish',
    templateUrl: './reset-password-finish.component.html',
})
export class ResetPasswordFinishComponent implements OnInit, AfterViewInit {
    readonly passwordMinLength: string;
    readonly passwordMaxLength: string;

    isSaving: boolean;
    confirmPassword: string;
    keyAndPassword: KeyAndPassword;

    constructor(
        private resetPasswordService: ResetPasswordService,
        private router: Router,
        private route: ActivatedRoute,
        private elementRef: ElementRef /*  private renderer: Renderer,
        private alertService: JhiAlertService,
        private alertTriggerService: AlertTriggerService*/
    ) {
        this.isSaving = false;
        this.keyAndPassword = new KeyAndPassword();
    }

    ngOnInit() {
        this.route.queryParams.subscribe((params) => {
            this.keyAndPassword.key = params['key'];
        });
    }

    ngAfterViewInit() {
        if (this.elementRef.nativeElement.querySelector('#password') != null) {
            //  this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#password'), 'focus', []);
        }
    }

    finishReset() {
        if (this.keyAndPassword.newPassword !== this.confirmPassword) {
            //     this.alertService.error('error.password.doNotMatch');
        } else {
            this.isSaving = true;
            this.resetPasswordService.changePasswordReset(this.keyAndPassword).subscribe(
                (response: string) => this.onSaveSuccess(),
                (error) => this.onSaveError()
            );
        }
    }

    private onSaveSuccess(response?: string) {
        this.isSaving = false;
        //   this.alertTriggerService.success('passwordReset.finish.messages.success', undefined, () => this.navigateToLogin(), 5000);
    }

    private onSaveError(error?) {
        this.isSaving = false;
    }

    navigateToLogin() {
        this.router.navigate(['login']);
    }
}
