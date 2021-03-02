import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { TranslateService } from '@ngx-translate/core';
import { ERROR_ALERT_KEY } from './app.constants';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {
    public errorAlertKey = ERROR_ALERT_KEY;

    constructor(private translateService: TranslateService, private titleService: Title) {
    }

    ngOnInit() {
        this.translateService.get('app.name.complete').subscribe((appName) => {
            this.titleService.setTitle(appName);
        });
    }
}
