import { Component, EventEmitter, OnDestroy, Output } from '@angular/core';
import { FilterFilter } from '@app/modules/filter/filter-search/filter-search';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
    selector: 'app-filter-search',
    templateUrl: 'filter-search.component.html',
})
export class FilterSearchComponent implements OnDestroy {
    @Output()
    public onFilterUpdate = new EventEmitter<FilterFilter>();
    public filters = new FilterFilter();

    private debouncer = new Subject<unknown>();

    constructor() {
        this.debouncer.pipe(debounceTime(300)).subscribe(() => this.onFilterUpdate.emit(this.filters));
    }

    public emit(): void {
        this.debouncer.next();
    }

    public resetFilters(): void {
        this.filters.reset();
        this.onFilterUpdate.emit(this.filters);
    }

    public ngOnDestroy(): void {
        this.debouncer.unsubscribe();
    }
}
