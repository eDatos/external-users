import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Language } from '@app/core/model';
import { StructuralResourcesTreeComponent } from '@app/shared/components/structural-resources-tree/structural-resources-tree.component';
import { TranslateService } from '@ngx-translate/core';
import { ArteEventManager } from 'arte-ng/services';
import { Subject, Subscription } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { ExternalUserFilter } from './external-user-filter';

@Component({
    selector: 'app-external-user-search',
    templateUrl: 'external-user-search.component.html',
})
export class ExternalUserSearchComponent implements OnInit, OnDestroy {
    @Input()
    public filters: ExternalUserFilter;
    public subscription: Subscription;
    public languageEnum = Language;
    public selectedCategoryResources: any[];

    @ViewChild(StructuralResourcesTreeComponent)
    public tree: StructuralResourcesTreeComponent;

    private filterChangesSubject: Subject<any> = new Subject<any>();
    private selectingCategory = false;

    constructor(private eventManager: ArteEventManager, private translateService: TranslateService) {
        this.selectedCategoryResources = [];
    }

    º;

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
        this.resetCategories();
    }

    public openCategoryModal() {
        if (!this.selectingCategory) {
            this.selectingCategory = true;
        }
        if (this.selectedCategoryResources?.length > 0) {
            this.tree.selectedResources = this.selectedCategoryResources;
        }
    }

    public closeCategoryModal() {
        this.selectingCategory = false;
    }

    public isSelectingCategory(): boolean {
        return this.selectingCategory;
    }

    public selectCategories() {
        const categories: string[] = [];
        this.selectedCategoryResources = this.tree.selectedResources;
        this.tree.selectedResources.forEach((category) => categories.push(category.data.id));
        this.filters.categories = categories;
        this.closeCategoryModal();
        this.filter();
    }

    public resetCategories() {
        this.filters.categories = [];
        this.selectedCategoryResources = [];
        this.filter();
    }

    public categoriesSelectedInfo(): string {
        const info: string = this.translateService.instant('externalUser.filters.placeholders.categoriesSelected');
        return info.replace('#', this.filters.categories != null ? this.filters.categories.length.toString() : '0');
    }
}
