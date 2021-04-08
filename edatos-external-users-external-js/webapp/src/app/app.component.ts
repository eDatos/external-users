import { Component, OnInit } from '@angular/core';
import { ERROR_ALERT_KEY } from './app.constants';
import 'reflect-metadata';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {
    public errorAlertKey = ERROR_ALERT_KEY;

    constructor() {}

    ngOnInit() {}
}
