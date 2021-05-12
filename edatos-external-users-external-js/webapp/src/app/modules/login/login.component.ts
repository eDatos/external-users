import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Credentials, User } from '@app/core/model';
import { TOKEN_AUTH_NAME } from '@app/app.constants';
import { AccountUserService } from '@app/core/service/user';
import { Principal } from '@app/core/service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {
    credentials: Credentials;

    constructor(private accountUserService: AccountUserService, private router: Router, private principal: Principal) {
        this.credentials = new Credentials();
    }

    ngOnInit() {
        console.log('hello');
    }

    login() {
        this.accountUserService.login(this.credentials).subscribe((foo: any) => {
            this.principal.identity().then(() => this.navigateToFilter());
        });
    }

    private navigateToFilter() {
        this.router.navigate(['favorite']);
    }

    public navigateToSignup() {
        this.router.navigate(['signup']);
    }

    public navigateToDataProtectionPolicy() {
        this.router.navigate(['data-protection-policy']);
    }
}
