import { Component, Inject, OnInit, Renderer2, RendererFactory2 } from '@angular/core';
import { ConfigService } from '@app/config';
import { CookieService } from 'ngx-cookie-service';
import { AVAILABLE_LANGUAGES, LANG_KEY } from '@app/app.constants';
import { LanguageService } from '@app/shared/service';
@Component({
    selector: 'app-switch-locale',
    templateUrl: './switch-locale.component.html',
    styleUrls: ['./switch-locale.component.css'],
})
export class SwitchLocaleComponent implements OnInit {
    public selectedLanguage: any;
    public languages: any;
    public origin: String;

    private dictionary = { es: 'Castellano', en: 'English', ca: 'Català' };

    renderer: Renderer2 = null;
    constructor(private cookieService: CookieService, private configService: ConfigService, private languageService: LanguageService) {}

    ngOnInit() {
        this.languages = AVAILABLE_LANGUAGES.map((locale) => ({ key: locale, name: this.dictionary[locale] }))
            .filter((language) => !!language.name)
            .sort((a, b) => (a.name > b.name ? 1 : -1));

        const locale = this.cookieService.get(LANG_KEY) || this.configService.getConfig().metadata.defaultLanguage;
        this.selectedLanguage = { key: locale, name: this.dictionary[locale] };
    }

    public onChangeLang(event: any) {
        this.cookieService.set(LANG_KEY, event.value.key);
        this.languageService.changeLanguage(event.value.key);
    }
}
