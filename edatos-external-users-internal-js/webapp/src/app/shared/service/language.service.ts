import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class LanguageService {
    public readonly resourceUrl = 'api/languages';

    constructor(private http: HttpClient) {}

    public getAllowed(): Observable<string[]> {
        return this.http.get<string[]>(`${this.resourceUrl}/allowed`);
    }
}
