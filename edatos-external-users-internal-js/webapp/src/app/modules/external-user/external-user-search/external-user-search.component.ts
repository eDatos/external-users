import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Language } from '@app/core/model';
import { ArteEventManager } from 'arte-ng/services';

import { Subject, Subscription } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { ExternalUserFilter } from './external-user-filter';

@Component({
    selector: 'app-external-user-search',
    templateUrl: 'external-user-search.component.html',
})
export class ExternalUserSearchComponent implements OnInit, OnDestroy {
    @Input()
    public filters: ExternalUserFilter;
    public subscription: Subscription;
    public languageEnum = Language;

    private filterChangesSubject: Subject<any> = new Subject<any>();

    constructor(private eventManager: ArteEventManager) {}

    public ngOnInit() {
        this.subscription = this.filterChangesSubject.pipe(debounceTime(300)).subscribe(() =>
            this.eventManager.broadcast({
                name: 'userSearch',
                content: this.filters,
            })
        );
    }

    public ngOnDestroy() {
        this.eventManager.destroy(this.subscription);
    }

    public filter(): void {
        this.filterChangesSubject.next();
    }

    public resetFilters(): void {
        this.filters.reset();
        this.filter();
    }
}
