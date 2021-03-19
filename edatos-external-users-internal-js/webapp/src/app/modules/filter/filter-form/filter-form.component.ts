import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { Filter, FilterService } from '@app/shared';
import * as _ from 'lodash';

@Component({
    selector: 'app-filter-form',
    templateUrl: './filter-form.component.html',
})
export class FilterFormComponent implements OnInit {
    public inEditMode = false;
    public filter: Filter;

    constructor(private filterService: FilterService, private activatedRoute: ActivatedRoute, private titleService: Title) {
        this.filter = this.activatedRoute.snapshot.data['filter'] ?? new Filter();

        this.activatedRoute.url.subscribe((segments) => {
            const lastUrlSegment = _.last(segments)?.path;
            this.inEditMode = this.inEditMode || lastUrlSegment === 'new';
            console.log(this.inEditMode);
        });
    }

    public ngOnInit() {
        if (!this.inEditMode) {
            this.titleService.setTitle(this.titleService.getTitle() + ' ' + this.filter.id);
        }
    }

    public toggleEditMode(): void {
        this.inEditMode = !this.inEditMode;
        console.log(this.inEditMode);
    }

    public submit(): void {
        if (this.filter.id == null) {
            this.filterService.save(this.filter).subscribe(this.done);
        } else {
            this.filterService.update(this.filter).subscribe(this.done);
        }
    }

    private done(filter: Filter) {
        this.filter = filter;
    }
}
