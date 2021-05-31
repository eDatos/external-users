import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Credentials } from '@app/core/model';
import { AccountUserService } from '@app/core/service/user';
import { AuthServerProvider, Principal } from '@app/core/service';
import { DOCUMENT } from '@angular/common';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    credentials: Credentials;
    origin: String;

    constructor(private accountUserService: AccountUserService, private router: Router, private principal: Principal, private route: ActivatedRoute, 
                private authServerProvider: AuthServerProvider, @Inject(DOCUMENT) readonly document: Document) {
        this.credentials = new Credentials();
        this.route.queryParams.subscribe(queryParams => {
            if(queryParams["origin"]) {
                this.origin = queryParams["origin"].replace(/^http:\/\//i, 'https://');
            }
        });
    }

    ngOnInit() {}

    login() {
        this.accountUserService.login(this.credentials).subscribe((foo: any) => {
            this.principal.identity().then(() => {
                if(this.origin) {
                    this.navigateToOrigin();
                } else {
                    this.navigateToFilter();
                }
            });
        });
    }

    private navigateToFilter() {
        this.router.navigate(['filter']);
    }

    private navigateToOrigin() {
        this.document.defaultView.open(this.origin + "?token=" + encodeURIComponent(this.authServerProvider.getToken()), "_self");
    }

    public navigateToSignup() {
        this.router.navigate(['signup']);
    }

    public navigateToDataProtectionPolicy() {
        this.router.navigate(['data-protection-policy']);
    }
}
