import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ITEMS_PER_PAGE, PAGINATION_OPTIONS } from '@app/app.constants';
import { LazyLoadEvent } from 'primeng/api';
import { ResponseWrapper } from 'arte-ng/src/lib/model';
import { ArteEventManager } from 'arte-ng/src/lib/services';
import { ConfigService } from '@app/config';

@Component({
    selector: 'app-hello',
    templateUrl: './hello.component.html',
})
export class HelloComponent implements OnInit {
    public title = 'Hello! Application';

    constructor() {}

    ngOnInit(): void {}
}
