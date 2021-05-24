import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Language } from '@app/core/model';
import { ArteEventManager, GenericModalService } from 'arte-ng/services';
import { Subject, Subscription } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { ExternalUserFilter } from './external-user-filter';
import { StructuralResourcesTreeComponent } from '@app/shared/components/structural-resources-tree/structural-resources-tree.component';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'app-external-user-search',
    templateUrl: 'external-user-search.component.html',
})
export class ExternalUserSearchComponent implements OnInit, OnDestroy {
    @Input()
    public filters: ExternalUserFilter;
    public subscription: Subscription;
    public languageEnum = Language;

    private filterChangesSubject: Subject<any> = new Subject<any>();
    private selectingCategory: boolean = false;

    private selectedCategoryResources: any[];

    constructor(private eventManager: ArteEventManager, private translateService: TranslateService) {
        this.selectedCategoryResources = [];
    }

    @ViewChild(StructuralResourcesTreeComponent)
    public tree: StructuralResourcesTreeComponent;

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
        let categories = [];
        this.selectedCategoryResources = this.tree.selectedResources;
        this.tree.selectedResources.forEach((category) => categories.push(category.data.id));
        this.filters.categories = categories;
        this.closeCategoryModal();
        this.filter();
    }

    public resetCategories() {
        this.filters.categories = [];
        this.selectedCategoryResources = null;
        this.filter();
    }

    public categoriesSelectedInfo(): string {
        let info: string = this.translateService.instant('externalUser.filters.placeholders.categoriesSelected');
        return info.replace('#', this.filters.categories != null ? this.filters.categories.length.toString() : '0');
    }
}
