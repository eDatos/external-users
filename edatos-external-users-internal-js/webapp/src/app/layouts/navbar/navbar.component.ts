import { Component, OnInit } from '@angular/core';
import { VERSION } from '@app/app.constants';
import { ConfigService } from '@app/config';
import { LoginService, PermissionService, Principal, ProfileService } from '@app/core/service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
    public inProduction: boolean;
    public isNavbarCollapsed: boolean;
    public modalRef: NgbModalRef;
    public version: string;

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

    public ngOnInit() {
        this.profileService.getProfileInfo().subscribe((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
        });
    }

    public collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    public isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    public logout() {
        this.collapseNavbar();
        this.loginService.logout();
        const config = this.configService.getConfig();
        window.location.href = config.cas.logout;
    }

    public toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    public correctlyLogged(): boolean {
        return Boolean(this.principal.correctlyLogged());
    }

    public canNavigateAdminMenu(): boolean {
        return this.permissionService.canNavigateAdminMenu();
    }
}
