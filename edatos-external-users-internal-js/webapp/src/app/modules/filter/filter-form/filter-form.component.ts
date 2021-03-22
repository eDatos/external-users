import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Filter, FilterService } from '@app/shared';
import { finalize } from 'rxjs/operators';

@Component({
    selector: 'app-filter-form',
    templateUrl: './filter-form.component.html',
})
export class FilterFormComponent implements OnInit {
    public inEditMode = false;
    public isSaving = false;
    public isLoading = false;
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

    public cancel(): void {
        this.updateData();
    }

    public edit(): void {
        this.toggleEditMode();
    }

    public submit(): void {
        this.toggleIsSaving();
        if (this.filter.id == null) {
            this.filterService
                .save(this.filter)
                .pipe(finalize(() => this.toggleIsSaving()))
                .subscribe((filter) => {
                    this.router.navigate([`../${filter.id}`], { relativeTo: this.activatedRoute });
                });
        } else {
            this.filterService
                .update(this.filter)
                .pipe(finalize(() => this.toggleIsSaving()))
                .subscribe((filter) => {
                    this.filter = filter;
                    this.toggleEditMode();
                });
        }
    }

    private toggleIsSaving(): void {
        this.isSaving = !this.isSaving;
    }

    private updateData(): void {
        this.showSpinner();
        this.filterService.get(this.filter.id).subscribe((filter) => {
            this.filter = filter;
            this.hideSpinner();
            this.toggleEditMode();
        });
    }

    private showSpinner(): void {
        this.isLoading = true;
    }

    private hideSpinner(): void {
        this.isLoading = false;
    }
}
