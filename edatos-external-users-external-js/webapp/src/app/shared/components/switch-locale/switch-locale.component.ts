import { Component, OnInit } from '@angular/core';
import { ConfigService } from '@app/config';
import { CookieService } from 'ngx-cookie-service';
import { LANG_KEY } from '@app/app.constants';
import { LanguageService } from '@app/shared/service';
import { PageTitleService } from '@app/core/service';

@Component({
    selector: 'app-switch-locale',
    templateUrl: './switch-locale.component.html',
    styleUrls: ['./switch-locale.component.css'],
})
export class SwitchLocaleComponent implements OnInit {
    public selectedLanguage: any;
    public languages: any;

    private dictionary = { es: 'Castellano', en: 'English', ca: 'Català' };

    constructor(private cookieService: CookieService, private configService: ConfigService, private languageService: LanguageService, private pageTitleService: PageTitleService) {}

    ngOnInit() {
        this.languages = this.configService.getConfig().metadata.availableLanguages.map((locale) => ({ key: locale, name: this.dictionary[locale] }));
        const locale = this.cookieService.get(LANG_KEY) || this.configService.getConfig().metadata.defaultLanguage;
        this.selectedLanguage = { key: locale, name: this.dictionary[locale] };
    }

    public onChangeLang(event: any) {
        this.cookieService.set(LANG_KEY, event.value.key);
        this.languageService.changeLanguage(event.value.key);
    }
}
