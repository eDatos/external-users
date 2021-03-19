import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Filter, FilterService } from '@app/shared';

@Component({
    selector: 'app-filter-form',
    templateUrl: './filter-form.component.html',
})
export class FilterFormComponent implements OnInit {
    public inEditMode = false;
    public filter: Filter;

    constructor(private filterService: FilterService, private activatedRoute: ActivatedRoute, private titleService: Title, private router: Router) {
        this.filter = this.activatedRoute.snapshot.data['filter'] ?? new Filter();

        this.activatedRoute.url.subscribe((segments) => {
            const lastUrlSegment = segments[segments.length - 1].path;
            this.inEditMode = this.inEditMode || lastUrlSegment === 'new';
        });
    }

    public ngOnInit() {
        if (!this.inEditMode) {
            this.titleService.setTitle(this.titleService.getTitle() + ' ' + this.filter.id);
        }
    }

    public toggleEditMode(): void {
        this.inEditMode = !this.inEditMode;
    }

    public submit(): void {
        if (this.filter.id == null) {
            this.filterService.save(this.filter).subscribe((filter) => {
                this.router.navigate([`../${filter.id}`], { relativeTo: this.activatedRoute });
            });
        } else {
            this.filterService.update(this.filter).subscribe((filter) => {
                this.filter = filter;
                this.toggleEditMode();
            });
        }
    }
}
