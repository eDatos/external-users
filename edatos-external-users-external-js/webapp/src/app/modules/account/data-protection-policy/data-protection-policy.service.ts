import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class DataProtectionPolicyService {

    private dataProtectionPolicyUrl = 'api/data-protection-policy';

    constructor(private http: HttpClient) { }

    getDataProtectionPolicy(): Observable<any> {
        return this.http.get(this.dataProtectionPolicyUrl);
    }
}
