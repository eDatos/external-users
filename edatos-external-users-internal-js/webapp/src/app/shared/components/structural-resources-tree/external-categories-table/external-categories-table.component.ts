import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ExternalCategory } from '@app/shared/model';
import { CategoryService } from '@app/shared/service';
import { LazyLoadEvent } from 'primeng/api';
import { finalize } from 'rxjs/operators';
import { Table } from 'primeng/table/table';

@Component({
    selector: 'app-external-categories-table',
    templateUrl: './external-categories-table.component.html',
    styleUrls: ['./external-categories-table.component.scss'],
})
export class ExternalCategoriesTableComponent implements OnInit {
    /**
     * The selected external categories.
     */
    @Input()
    public selectedExternalCategories: ExternalCategory[];

    /**
     * The external categories that must appear as disabled.
     */
    @Input()
    public disabledExternalCategories: ExternalCategory[];

    /**
     * List of external categories to be displayed in the table. If null,
     * they will be automatically loaded by the component.
     */
    @Input()
    public externalCategories: ExternalCategory[] = [];

    public totalItems: number;
    public loading = false;

    constructor(public categoryService: CategoryService) {}

    public ngOnInit(): void {
        if (!this.externalCategories) {
            this.update();
        }
    }

    public getSelectedResources(): ExternalCategory[] {
        return this.selectedExternalCategories;
    }

    public isDisabled(resource: ExternalCategory): boolean {
        return this.disabledExternalCategories.some((category) => category.urn === resource.urn);
    }

    public onSelectAll() {
        this.selectedExternalCategories = this.selectedExternalCategories.filter((externalCat) => !this.isDisabled(externalCat));
    }

    public lazyLoad(e: LazyLoadEvent): void {
        const page = e.first! / e.rows!;
        const size = e.rows;
        this.update({ page, size });
    }

    private update(params?): void {
        this.loading = true;
        this.categoryService
            .getExternalCategories(params)
            .pipe(finalize(() => (this.loading = false)))
            .subscribe((categories) => {
                this.totalItems = categories.totalCount()!;
                this.externalCategories = categories.body;
            });
    }
}
