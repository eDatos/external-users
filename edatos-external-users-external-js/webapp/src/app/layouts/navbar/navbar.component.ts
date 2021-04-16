import { Component, OnInit } from '@angular/core';
import { VERSION } from '@app/app.constants';
import { AccountUserService, LoginService, PermissionService, Principal, ProfileService } from '@app/core/service';
import { DeleteConfirmDialogComponent } from '@app/modules/account';
import { GenericModalService } from 'arte-ng/services';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    public isCorrectlyLogged: boolean;
    public isNavbarCollapsed: boolean;
    public version: string;
    public account: any;

    constructor(
        private loginService: LoginService,
        public permissionService: PermissionService,
        private principal: Principal,
        private profileService: ProfileService,
        private genericModalService: GenericModalService,
        private userService: AccountUserService
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.profileService.getProfileInfo().subscribe((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
        });
        this.principal.isCorrectlyLogged().subscribe((correctyLogged) => {
            this.isCorrectlyLogged = correctyLogged;
        });
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    public puedeNavegarAdministracion(): boolean {
        return this.permissionService.puedeNavegarAdministracion();
    }

    public openDeleteDialog() {
        this.userService
            .getLogueado()
            .toPromise()
            .then((account) => {
                const modalRef = this.genericModalService.open(DeleteConfirmDialogComponent as Component, { user: { ...account } }, { container: '.app' });
                modalRef.result.subscribe((isDelete) => this.onDeleteSuccess(isDelete));
            });
    }

    private onDeleteSuccess(isDelete: boolean) {
        if (isDelete) {
            this.collapseNavbar();
            this.loginService.logout();
        }
    }
}
