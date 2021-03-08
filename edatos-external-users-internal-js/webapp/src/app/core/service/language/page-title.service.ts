import { Injectable, Renderer2, RendererFactory2 } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { LangChangeEvent, TranslateService } from '@ngx-translate/core';
import { LANGUAGES } from './language.constants';

@Injectable()
export class PageTitleService {
    renderer: Renderer2 = null;

    constructor(private translateService: TranslateService, private rootRenderer: RendererFactory2, private titleService: Title, private router: Router) {
        this.renderer = rootRenderer.createRenderer(document.querySelector('html'), null);
        this.init();
    }

    getAll(): Promise<any> {
        return Promise.resolve(LANGUAGES);
    }

    /**
     * Update the window title using params in the following
     * order:
     * 1. titleKey parameter
     * 2. $state.$current.data.pageTitle (current state page title)
     * 3. 'global.title'
     */
    update(titleKey?: string) {
        if (!titleKey) {
            titleKey = this.get(this.router.routerState.snapshot.root);
        }

        this.translateService.get(titleKey).subscribe((title) => {
            this.titleService.setTitle(title);
        });
    }

    private init() {
        this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
            this.renderer.setAttribute(document.querySelector('html'), 'lang', this.translateService.currentLang);
            this.update();
        });
    }

    private get(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'app.name.complete';
        if (routeSnapshot.firstChild) {
            title = this.get(routeSnapshot.firstChild) || title;
        }
        return title;
    }
}
