import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { setCurrentLanguage } from '@app/core/service';
import { TranslateService } from '@ngx-translate/core';
import { LangChangeEvent } from '@ngx-translate/core/lib/translate.service';

@Injectable({
    providedIn: 'root',
})
export class LanguageService {
    constructor(private http: HttpClient, private translateService: TranslateService) {}

    public init(): void {
        setCurrentLanguage(this.translateService.currentLang);
        this.translateService.onLangChange.subscribe((e: LangChangeEvent) => {
            setCurrentLanguage(e.lang);
        });
    }
}
