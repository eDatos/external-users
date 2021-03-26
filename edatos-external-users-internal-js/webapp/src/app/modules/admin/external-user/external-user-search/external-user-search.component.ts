import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ArteEventManager } from 'arte-ng/services';

import { Subject, Subscription } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { ExternalUserFilter } from './external-user-filter';

@Component({
    selector: 'app-external-user-search',
    templateUrl: 'external-user-search.component.html',
})
export class ExternalUserSearchComponent implements OnInit, OnDestroy {
    private filterChangesSubject: Subject<any> = new Subject<any>();
    public subscription: Subscription;

    @Input()
    public filters: ExternalUserFilter;

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
