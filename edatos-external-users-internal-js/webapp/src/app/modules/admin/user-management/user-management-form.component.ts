import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserMgmtDeleteDialogComponent } from './user-management-delete-dialog.component';
import { User, Role, Treatment, Language } from '@app/core/model';
import { UserService } from '@app/core/service/user';
import { PermissionService } from '@app/core/service/auth';
import { GenericModalService, ArteEventManager } from 'arte-ng/services';

@Component({
    selector: 'app-user-mgmt-form',
    templateUrl: './user-management-form.component.html',
})
export class UserMgmtFormComponent implements OnInit, OnDestroy {
    public user: User;
    public isSaving: boolean;
    public usuarioValido = false;
    private subscription: Subscription;
    public paramLogin: string;
    public eventSubscriber: Subscription;
    public rolesEnum = Role;
    public languageEnum = Language;
    public treatmentEnum = Treatment;

    constructor(
        private userService: UserService,
        public permissionService: PermissionService,
        private genericModalService: GenericModalService,
        private eventManager: ArteEventManager,
        private route: ActivatedRoute,
        private router: Router
    ) {}

    public ngOnInit() {
        this.isSaving = false;

        this.subscription = this.route.params.subscribe((params) => {
            this.paramLogin = params['login'];
            this.load(this.paramLogin);
        });
        this.eventSubscriber = this.eventManager.subscribe(UserMgmtDeleteDialogComponent.EVENT_NAME, (response) => {
            this.user = Object.assign(new User(), response.content);
        });
    }

    public isEditMode(): boolean {
        const lastPath = this.route.snapshot.url[this.route.snapshot.url.length - 1].path;
        return lastPath === 'edit' || lastPath === 'user-management-new';
    }

    public load(login) {
        if (login) {
            this.userService.get(login).subscribe((user) => {
                this.user = user;
                this.userService.buscarUsuarioEnLdap(this.user.login).subscribe((usuarioLdap) => {
                    if (!!usuarioLdap) {
                        this.usuarioValido = true;
                    }
                });
            });
        } else {
            this.user = new User();
        }
    }

    public clear() {
        // const with arrays: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/const
        const returnPath = ['/admin', 'user-management'];
        if (this.paramLogin) {
            returnPath.push(this.paramLogin);
        }
        this.router.navigate(returnPath);
    }

    public save() {
        this.isSaving = true;
        if (this.existeUsuario()) {
            this.userService.update(this.user).subscribe(
                (response) => this.onSaveSuccess(response),
                () => this.onSaveError()
            );
        } else {
            this.userService.create(this.user).subscribe(
                (response) => this.onSaveSuccess(response),
                () => this.onSaveError()
            );
        }
    }

    public validarUsuario(inputDirty = true) {
        if (inputDirty) {
            if (this.user.login) {
                this.userService.buscarUsuarioEnLdap(this.user.login).subscribe(
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
                    }
                );
            }
        }
    }

    public delete() {
        this.genericModalService.open(UserMgmtDeleteDialogComponent as Component, { user: this.user });
    }

    public restore() {
        this.genericModalService.open(UserMgmtDeleteDialogComponent as Component, { user: this.user });
    }

    private onSaveSuccess(result) {
        this.eventManager.broadcast({ name: 'userListModification', content: 'OK' });
        this.isSaving = false;
        this.router.navigate(['/admin', 'user-management', this.user.login]);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    public existeUsuario(): boolean {
        return !!this.user.id;
    }

    public ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
