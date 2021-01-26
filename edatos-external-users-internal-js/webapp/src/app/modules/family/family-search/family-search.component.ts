import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subject, Subscription } from 'rxjs';
import { FamilyFilter } from './family-filter.model';
import { debounceTime } from 'rxjs/operators';
import { ArteEventManager } from 'arte-ng/src/lib/services';

@Component({
    selector: 'ac-family-search',
    templateUrl: './family-search.component.html'
})
export class FamilySearchComponent implements OnInit, OnDestroy {
    public static EVENT_NAME = 'familySearch';
    private filterChangesSubject: Subject<any> = new Subject<any>();
    subscription: Subscription;

    @Input()
    filters: FamilyFilter;

    constructor(private eventManager: ArteEventManager) { }

    ngOnInit() {
        this.subscription = this.filterChangesSubject
            .pipe(debounceTime(300))
            .subscribe(() =>
                this.eventManager.broadcast({
                    name: FamilySearchComponent.EVENT_NAME,
                    content: this.filters
                })
            );
    }

    ngOnDestroy() {
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
