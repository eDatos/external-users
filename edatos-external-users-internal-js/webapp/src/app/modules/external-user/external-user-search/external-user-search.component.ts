import { ChangeDetectorRef, Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Language } from '@app/core/model';
import { FavoriteResource, StructuralResourcesTreeComponent } from '@app/shared/components/structural-resources-tree/structural-resources-tree.component';
import { CategoryService } from '@app/shared/service';
import { ArteEventManager } from 'arte-ng/services';
import * as _ from 'lodash';
import { of, Subject, Subscription } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { ExternalUserFilter } from './external-user-filter';

@Component({
    selector: 'app-external-user-search',
    templateUrl: 'external-user-search.component.html',
    styleUrls: ['external-user-search.component.scss'],
})
export class ExternalUserSearchComponent implements OnInit, OnDestroy {
    @Input()
    public filters: ExternalUserFilter;

    public subscription: Subscription;
    public languageEnum = Language;
    public selectedResources: FavoriteResource[] = [];
    public isSelectingResource = false;

    @ViewChild(StructuralResourcesTreeComponent)
    public tree: StructuralResourcesTreeComponent;

    private filterChangesSubject: Subject<any> = new Subject<any>();

    constructor(private eventManager: ArteEventManager, public categoryService: CategoryService, private changeDetectorRef: ChangeDetectorRef) {}

    public ngOnInit() {
        this.subscription = this.filterChangesSubject.pipe(debounceTime(300)).subscribe(() =>
            this.eventManager.broadcast({
                name: 'userSearch',
                content: this.filters,
            })
        );
        this.categoryService.findAll({ size: 100000000 }).subscribe((categories) => {
            this.selectedResources = [
                ...categories.body
                    .filter((category) => {
                        return (
                            this.filters.categories.includes(category.id.toString()) ||
                            category.externalOperations.some((externalOp) => {
                                return this.filters.externalOperations.includes(externalOp.id.toString());
                            })
                        );
                    })
                    .map((category) => {
                        if (this.filters.categories.includes(category.id.toString())) {
                            return category;
                        } else {
                            return category.externalOperations
                                .filter((externalOp) => {
                                    return this.filters.externalOperations.indexOf(externalOp.id.toString()) !== -1;
                                })
                                .shift()!;
                        }
                    }),
            ];
        });
    }

    public ngOnDestroy() {
        this.eventManager.destroy(this.subscription);
    }

    public trackSelectedResource(index: number, item: FavoriteResource) {
        return `${item.id}${item.favoriteType}`;
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
        this.isSelectingResource = true;
    }

    public closeCategoryModal() {
        this.isSelectingResource = false;
    }

    public selectCategories() {
        const categoriesIds: string[] = [];
        const externalOperationsIds: string[] = [];
        for (const resource of this.tree.selectedResources) {
            if (resource.data.favoriteType === 'category') {
                categoriesIds.push(resource.data.id.toString());
            } else {
                externalOperationsIds.push(resource.data.id.toString());
            }
        }
        this.filters.categories = categoriesIds;
        this.filters.externalOperations = externalOperationsIds;
        this.updateSelectedResources();
        this.closeCategoryModal();
        this.filter();
    }

    public resetCategories() {
        this.filters.categories = [];
        this.filters.externalOperations = [];
        this.selectedResources = [];
        this.filter();
    }

    public updateSelectedResources() {
        this.selectedResources = this.tree.selectedResources.map((node) => node.data);
    }

    public deleteResource(resource: FavoriteResource) {
        _.pull(this.selectedResources, resource);
        let filter;
        if (resource.favoriteType === 'category') {
            filter = this.filters.categories;
        } else {
            filter = this.filters.externalOperations;
        }
        _.pull(filter, resource.id.toString());
        of(debounceTime(300)).subscribe(() => this.filter());
    }
}
