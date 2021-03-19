import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { Filter } from '@app/shared';
import * as _ from 'lodash';

@Component({
    selector: 'app-filter-form',
    templateUrl: './filter-form.component.html',
})
export class FilterFormComponent implements OnInit {
    public inEditionMode = false;
    public filter: Filter;

    constructor(private activatedRoute: ActivatedRoute, private titleService: Title) {
        this.filter = this.activatedRoute.snapshot.data['filter'] ?? new Filter();
        this.activatedRoute.url.subscribe((segments) => {
            this.inEditionMode = _.last(segments)?.path === 'edit';
        });
    }

    public ngOnInit(): void {
        if (this.inEditionMode) {
            this.titleService.setTitle(this.titleService.getTitle() + ' ' + this.filter.id);
        }
    }

    public submit(): void {
        console.log('Filter submitted', this.filter);
    }

    // FIXME(EDATOS-3280): Remove if not used
    // private toggleEditionMode(segments: UrlSegment[]): void {
    //     this.inEditionMode = !this.inEditionMode;
    // }
}
