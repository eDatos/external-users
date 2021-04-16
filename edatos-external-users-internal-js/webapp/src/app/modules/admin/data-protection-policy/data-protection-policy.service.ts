import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { InternationalString } from '@app/shared/model';
import { map } from 'rxjs/operators';

@Injectable()
export class DataProtectionPolicyService {

    private dataProtectionPolicyUrl = 'api/data-protection-policy';

    constructor(private http: HttpClient) { }

    getDataProtectionPolicy(): Observable<InternationalString> {
        return this.http.get(this.dataProtectionPolicyUrl).pipe(map((response: any) => {
            let internationalDataProtectionPolicy = new InternationalString();
            internationalDataProtectionPolicy.id = response.id;
            internationalDataProtectionPolicy.texts = response.value.texts;
            return internationalDataProtectionPolicy;
        }));
    }

    updateDataProtectionPolicy(value: InternationalString): Observable<InternationalString> {
        return this.http.put(this.dataProtectionPolicyUrl, value).pipe(map((response: any) => {
            let internationalDataProtectionPolicy = new InternationalString();
            internationalDataProtectionPolicy.id = response.id;
            internationalDataProtectionPolicy.texts = response.value.texts;
            return internationalDataProtectionPolicy;
        }));
    }
}
