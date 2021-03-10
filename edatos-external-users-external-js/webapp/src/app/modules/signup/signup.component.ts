import { DatePipe } from '@angular/common';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { User, Role, Treatment, Language } from '@app/core/model';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '@app/core/service/user';
import { Subscription } from 'rxjs';
import { GenericModalService, ArteEventManager } from 'arte-ng/src/lib/services';

type SignUp = Omit<User, 'id' | 'roles'>;

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
})
export class SignupFormComponent implements OnInit, OnDestroy {
    user: User;
    isSaving: Boolean;
    usuarioValido = false;
    private subscription: Subscription;
    paramLogin: string;
    public rolesEnum = Role;
    languageEnum = Language;
    treatmentEnum = Treatment;

    confirmPassword: string;

    constructor(
        private userService: UserService,
        private genericModalService: GenericModalService,
        private eventManager: ArteEventManager,
        private route: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.user = new User();
    }

    clear() {
        const returnPath = ['signup'];
        if (this.paramLogin) {
            returnPath.push(this.paramLogin);
        }
        this.router.navigate(returnPath);
    }

    save() {
        this.isSaving = true;
        if (this.existeUsuario() || this.passwordDoNotMatch()) {
            this.usuarioValido = false;
        } else {
            this.userService.create(this.user).subscribe(
                (response) => this.onSaveSuccess(response),
                () => this.onSaveError()
            );
        }
    }

    validarUsuario(inputDirty = true) {
        if (inputDirty) {
            if (this.user.email) {
                this.userService.buscarUsuarioPorEmail(this.user.email).subscribe(
                    (usuario) => {
                        if (usuario) {
                            this.user = usuario;
                            this.usuarioValido = true;
                        } else {
                            this.usuarioValido = false;
                        }
                    },
                    (error) => {
                        this.usuarioValido = false;
                        console.log(error);
                    }
                );
            }
        }
    }

    private passwordDoNotMatch(): boolean {
        return this.user.password !== this.confirmPassword;
    }

    private onSaveSuccess(result) {
        console.log(result);
        this.isSaving = false;
    }

    private onSaveError() {
        this.isSaving = false;
    }

    public existeUsuario(): boolean {
        return !!this.user.id;
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
