import { AfterViewInit, Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { Router } from '@angular/router';
import { ResetPasswordService } from '@app/core/service';

@Component({
    selector: 'jhi-password-reset-init',
    templateUrl: './reset-password-init.component.html',
})
export class ResetPasswordInitComponent implements OnInit, AfterViewInit {
    isSending: boolean;

    resetAccount: any;

    constructor(
        private resetPasswordService: ResetPasswordService,
        private elementRef: ElementRef,
        private renderer: Renderer2,
        private router: Router //private alertTriggerService: AlertTriggerService
    ) {
        this.isSending = false;
    }

    ngOnInit() {
        this.resetAccount = {};
    }

    ngAfterViewInit() {
        //   this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#email'), 'focus', []);
    }

    requestReset() {
        this.isSending = true;
        this.resetPasswordService.resetPassword(this.resetAccount.email).subscribe(
            (response: string) => this.onSendingSuccess(),
            (error) => this.onSendingError()
        );
    }

    private onSendingSuccess(response?: string) {
        this.isSending = false;
        //  this.alertTriggerService.success('passwordReset.init.messages.success', { email: this.resetAccount.email }, () => this.navigateToLogin(), 5000);
    }

    private onSendingError(error?: any) {
        this.isSending = false;
    }

    navigateToLogin() {
        this.router.navigate(['login']);
    }
}
