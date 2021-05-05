import { Component, Input, OnInit } from '@angular/core';
import { ExternalCategory } from '@app/shared/model/external-category.model';
import { CategoryService } from '@app/shared/service';
import { shareReplay } from 'rxjs/operators';

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
     * List of external categories to be displayed in the table. If null,
     * they will be automatically loaded by the component.
     */
    @Input()
    public externalCategories: ExternalCategory[];

    constructor(public categoryService: CategoryService) {}

    public ngOnInit(): void {
        if (!this.externalCategories) {
            this.categoryService
                .getExternal()
                .pipe(shareReplay({ bufferSize: 1, refCount: true }))
                .subscribe((categories) => (this.externalCategories = categories));
        }
    }

    public getSelectedResources(): ExternalCategory[] {
        return this.selectedExternalCategories;
    }
}
