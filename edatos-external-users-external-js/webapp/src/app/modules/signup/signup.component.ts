import { Component, OnInit } from '@angular/core';
import { User, Role, Treatment, Language } from '@app/core/model';
import { Router } from '@angular/router';
import { AccountUserService } from '@app/core/service/user';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

type SignUp = Omit<User, 'id' | 'roles'>;

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
})
export class SignupFormComponent implements OnInit {
    public user: User = new User();
    public isSaving: Boolean = false;
    public validUser = false;
    private paramLogin: string;
    public rolesEnum = Role;
    public languageEnum = Language;
    public treatmentEnum = Treatment;

    public confirmPassword: string;

    constructor(private accountUserService: AccountUserService, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        const returnPath = ['signup'];
        if (this.paramLogin) {
            returnPath.push(this.paramLogin);
        }
        this.router.navigate(returnPath);
    }

    save(modalContent) {
        this.isSaving = true;
        if (this.passwordDoNotMatch()) {
            this.validUser = false;
        } else {
            this.modalService.open(modalContent, { container: '.app' });
            this.accountUserService.create("captchaContainer", this.user)
                .then((response) => this.onSaveSuccess(response))
                .catch(() => this.onSaveError())
                .finally(() => {
                    this.closeCaptchaModal();
                });
        }
    }

    closeCaptchaModal() {
        this.modalService.dismissAll();
        this.isSaving = false;
    }

    validarUsuario(inputDirty = true) {
        if (inputDirty) {
            if (this.user.email) {
                this.accountUserService.buscarUsuarioPorEmail(this.user.email).subscribe(
                    (usuario) => {
                        if (usuario) {
                            this.user = usuario;
                            this.validUser = true;
                        } else {
                            this.validUser = false;
                        }
                    },
                    (error) => {
                        this.validUser = false;
                        console.log(error);
                    }
                );
            }
        }
    }

    private passwordDoNotMatch(): boolean {
        return this.user.password !== this.confirmPassword;
    }

    navigateToLogin() {
        this.router.navigate(['login']);
    }

    private onSaveSuccess(result) {
        this.isSaving = false;
        this.navigateToLogin();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
