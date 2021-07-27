import { Injectable } from '@angular/core';

@Injectable()
export class ExternalLoginService {
    private _urlToReturnToAfterLogin: string;

    constructor() {}

    public set urlToReturnToAfterLogin(urlToReturnToAfterLogin : string) {
        this._urlToReturnToAfterLogin = urlToReturnToAfterLogin;
    }
    
    public get urlToReturnToAfterLogin() : string {
        return this._urlToReturnToAfterLogin;
    }
}
