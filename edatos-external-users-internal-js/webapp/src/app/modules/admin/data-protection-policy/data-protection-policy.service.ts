import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { InternationalString } from '@app/shared/model';

@Injectable()
export class DataProtectionPolicyService {

    constructor(private http: HttpClient) { }

    getDataProtectionPolicy(): Observable<any> {
        return this.http.get('/api/data-protection-policy');
    }

    updateDataProtectionPolicy(value: InternationalString): Observable<any> {
        return this.http.put('/api/data-protection-policy', value);
    }
}
