import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VERSION } from '@app/app.constants';
import { ConfigService } from '@app/config';
import { LoginService, PermissionService, Principal, ProfileService } from '@app/core/service';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.component.scss']
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    modalRef: NgbModalRef;
    version: string;

    constructor(
        private loginService: LoginService,
        public permissionService: PermissionService,
        private principal: Principal,
        private profileService: ProfileService,
        private configService: ConfigService
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.profileService.getProfileInfo().subscribe((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
        });
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        const config = this.configService.getConfig();
        window.location.href = config.cas.logout;
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    public correctlyLogged(): boolean {
        return Boolean(this.principal.correctlyLogged());
    }

    public puedeNavegarAdministracion(): boolean {
        return this.permissionService.puedeNavegarAdministracion();
    }
}
