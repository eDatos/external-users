import { Component, OnInit } from '@angular/core';
import { Credentials } from '@app/core/model';
import { Router } from '@angular/router';
import { UserService } from '@app/core/service/user';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {
    credentials: Credentials;

    constructor(private userService: UserService, private router: Router) {
        this.credentials = new Credentials();
    }

    ngOnInit() {}

    login() {
        this.navigateToSignup();
    }
    private navigateTo() {
        /* TODO EDATOS-3287 Still to be set up */
    }

    public navigateToSignup() {
        this.router.navigate(['signup']);
    }
}
