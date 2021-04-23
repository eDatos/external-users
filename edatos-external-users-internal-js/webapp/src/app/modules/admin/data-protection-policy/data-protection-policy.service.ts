import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { DataProtectionPolicy } from '@app/shared/model/data-protection-policy.model';
import { convert } from '@app/core/utils/response-utils';

@Injectable()
export class DataProtectionPolicyService {

    private dataProtectionPolicyUrl = 'api/data-protection-policy';

    constructor(private http: HttpClient) { }

    getDataProtectionPolicy(): Observable<DataProtectionPolicy> {
        return this.http.get(this.dataProtectionPolicyUrl).pipe(map((response: any) => convert(DataProtectionPolicy, response)));
    }

    updateDataProtectionPolicy(value: DataProtectionPolicy): Observable<DataProtectionPolicy> {
        return this.http.put(this.dataProtectionPolicyUrl, value).pipe(map((response: any) => convert(DataProtectionPolicy, response)));
    }
}
