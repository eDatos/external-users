import { Component, OnInit, OnDestroy, Input } from '@angular/core';

import { Subject, Subscription } from 'rxjs';
import { UserFilter } from './user-filter.model';
import { debounceTime } from 'rxjs/operators';
import { Role } from '@app/core/model';
import { ArteEventManager } from 'arte-ng/services';

@Component({
    selector: 'ac-user-search',
    templateUrl: 'user-search.component.html'
})
export class UserSearchComponent implements OnInit, OnDestroy {
    private filterChangesSubject: Subject<any> = new Subject<any>();
    subscription: Subscription;

    @Input()
    public filters: UserFilter;
    public rolEnum = Role;

    constructor(private eventManager: ArteEventManager) { }

    ngOnInit() {
        this.subscription = this.filterChangesSubject
            .pipe(debounceTime(300))
            .subscribe(() =>
                this.eventManager.broadcast({
                    name: 'userSearch',
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
