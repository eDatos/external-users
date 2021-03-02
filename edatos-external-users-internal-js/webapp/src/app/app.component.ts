import { Component } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { PageTitleService } from '@app/core/service';
import { TranslateService } from '@ngx-translate/core';
import { ERROR_ALERT_KEY } from './app.constants';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
})
export class AppComponent {
    public errorAlertKey = ERROR_ALERT_KEY;

    constructor(private translateService: TranslateService, private titleService: Title, private pageTitleService: PageTitleService) {
        this.pageTitleService.update();
    }
}
