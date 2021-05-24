import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { convert } from '@app/core/utils/response-utils';
import { KeyAndPassword } from '@app/core/model/key-poassword.model';

@Injectable()
export class ResetPasswordService {
    private resourceUrl = 'api/recover-password';

    constructor(private http: HttpClient) {}

    resetPassword(email: string): Observable<String> {
        return this.http.post<String>(this.resourceUrl, email).pipe(map((res) => convert(String, res)));
    }

    changePasswordReset(keyAndPassword: KeyAndPassword): Observable<String> {
        console.log('ENTRO AL SERVICIO', keyAndPassword);
        return this.http.post<String>(`${this.resourceUrl}/change-password`, keyAndPassword).pipe(map((res) => convert(String, res)));
    }

    existsUserWithKey(key: string): Observable<Boolean> {
        return this.http.get<Boolean>(`${this.resourceUrl}/key-recovery/${key}`).pipe(map((res) => convert(Boolean, res)));
    }
}
