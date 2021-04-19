import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { InternationalString } from '@app/shared';
import { map } from 'rxjs/operators';
import { DataProtectionPolicy } from '@app/shared/model/data-protection-policy.model';

@Injectable()
export class DataProtectionPolicyService {

    private dataProtectionPolicyUrl = 'api/data-protection-policy';

    constructor(private http: HttpClient) { }

    getDataProtectionPolicy(): Observable<DataProtectionPolicy> {
        return this.http.get(this.dataProtectionPolicyUrl).pipe(map((response: any) => {
            let dataProtectionPolicy = response as DataProtectionPolicy;
            let internationalValue = new InternationalString();
            internationalValue.texts = response.value.texts;
            dataProtectionPolicy.value = internationalValue;

            return dataProtectionPolicy;
        }));
    }
}
