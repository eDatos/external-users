import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { setCurrentLanguage } from '@app/core/service';
import { TranslateService } from '@ngx-translate/core';
import { LangChangeEvent } from '@ngx-translate/core/lib/translate.service';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class LanguageService {
    public readonly resourceUrl = 'api/languages';

    constructor(private http: HttpClient, private translateService: TranslateService) {
        setCurrentLanguage(translateService.currentLang);
        translateService.onLangChange.subscribe((e: LangChangeEvent) => {
            setCurrentLanguage(e.lang);
        });
    }

    public getAllowed(): Observable<string[]> {
        return this.http.get<string[]>(`${this.resourceUrl}/allowed`);
    }
}
