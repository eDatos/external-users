import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Credentials } from '@app/core/model';
import { AccountUserService, ExternalLoginService } from '@app/core/service/user';
import { AuthServerProvider, Principal } from '@app/core/service';
import { DOCUMENT } from '@angular/common';
import { addQueryParamToRoute } from '@app/shared/utils/routesUtils';
import { CaptchaService } from '@app/shared/service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
    credentials: Credentials;
    origin: String;
    captchaContainerEl: ElementRef;

    @ViewChild('captchaContainer') 
    set captchaContainer(captchaContainerEl: ElementRef) {
        if(captchaContainerEl && !this.captchaContainerEl) {
            this.captchaContainerEl = captchaContainerEl;
            this.captchaService.buildCaptcha(this.captchaContainerEl);
        }
    }

    constructor(private accountUserService: AccountUserService, private router: Router, private principal: Principal, private route: ActivatedRoute, 
                private authServerProvider: AuthServerProvider, @Inject(DOCUMENT) readonly document: Document, private captchaService: CaptchaService, 
                private externalUserService: ExternalLoginService) {
        this.credentials = new Credentials();
        this.route.queryParams.subscribe(queryParams => {
            if(queryParams["origin"]) {
                this.origin = queryParams["origin"].replace(/^http:\/\//i, 'https://');
                this.externalUserService.urlToReturnToAfterLogin = this.origin.toString();
            } else {
                this.origin = this.externalUserService.urlToReturnToAfterLogin;
            }
        });
    }

    ngOnInit() {}

    login() {
        this.accountUserService.login(this.captchaContainerEl, this.credentials).then((foo: any) => {
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
        const originRouteWithTokenParam = addQueryParamToRoute(this.origin.toString(), "token", encodeURIComponent(this.authServerProvider.getToken()));
        this.document.defaultView.open(originRouteWithTokenParam, "_self");
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
