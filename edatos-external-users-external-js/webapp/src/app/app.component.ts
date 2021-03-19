import { Component, OnInit } from '@angular/core';
import { ERROR_ALERT_KEY } from './app.constants';
@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
    public errorAlertKey = ERROR_ALERT_KEY;

    constructor() {}

    ngOnInit() {}
}
