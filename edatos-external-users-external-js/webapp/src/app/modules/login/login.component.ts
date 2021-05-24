import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Credentials } from '@app/core/model';
import { AccountUserService } from '@app/core/service/user';
import { Principal } from '@app/core/service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
    credentials: Credentials;

    constructor(private accountUserService: AccountUserService, private router: Router, private principal: Principal) {
        this.credentials = new Credentials();
    }

    ngOnInit() {}

    login() {
        this.accountUserService.login(this.credentials).subscribe((foo: any) => {
            this.principal.identity().then(() => this.router.navigate(['filter']));
        });
    }

    public navigateToSignup() {
        this.router.navigate(['signup']);
    }

    public navigateToDataProtectionPolicy() {
        this.router.navigate(['data-protection-policy']);
    }

    public navigateToResetPassword() {
        this.router.navigate(['reset-password']);
    }
}
