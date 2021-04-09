import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VERSION } from '@app/app.constants';
import { ConfigService } from '@app/config';
import { LoginService, PermissionService, Principal, ProfileService } from '@app/core/service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    public isCorrectlyLogged: boolean;
    public isNavbarCollapsed: boolean;
    private modalRef: NgbModalRef;
    public version: string;

    constructor(
        private loginService: LoginService,
        public permissionService: PermissionService,
        private principal: Principal,
        private profileService: ProfileService,
        private configService: ConfigService,
        private router: Router
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
}
