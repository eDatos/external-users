import { Component, DoCheck, EventEmitter, Input, IterableDiffer, IterableDiffers, OnInit, Output } from '@angular/core';
import { Category, Favorite, Operation } from '@app/shared/model';
import { CategoryService } from '@app/shared/service/category/category.service';
import { OperationService } from '@app/shared/service/operation/operation.service';
import { TranslateService } from '@ngx-translate/core';
import { ArteAlertService } from 'arte-ng/services';
import { TreeNode } from 'primeng/api';
import { Observable, of } from 'rxjs';

@Component({
    selector: 'app-structural-resources-tree',
    templateUrl: './structural-resources-tree.component.html',
})
export class StructuralResourcesTreeComponent implements OnInit, DoCheck {
    /**
     * The list of favorites. The selection of the tree is automatically updated when favorites
     * are added or removed.
     */
    @Input()
    public favorites: Favorite[];

    /**
     * If true, the elements on the tree become unselectable.
     */
    @Input()
    public disabled = false;

    /**
     * Sets the selection method of the tree.
     *
     * @see https://www.primefaces.org/primeng/v9-lts/#/tree
     */
    @Input()
    public selectionMode: 'checkbox' | 'single' | 'multiple' = 'checkbox';

    /**
     * Emits an event when an element of the tree is selected.
     */
    @Output()
    public onResourceSelect = new EventEmitter<Category | Operation>();

    /**
     * Emits an event when an element of the tree is unselected.
     */
    @Output()
    public onResourceUnselect = new EventEmitter<Category | Operation>();

    public resources: TreeNode[];
    public selectedResources: TreeNode[] = [];

    private mainLanguageCode: string;
    private tree: Category[];
    private iterableDiffer: IterableDiffer<Favorite>;
    private nodeList: TreeNode[] = [];

    constructor(
        private alertService: ArteAlertService,
        private categoryService: CategoryService,
        private operationService: OperationService,
        private translateService: TranslateService,
        private iterableDiffers: IterableDiffers
    ) {
        this.iterableDiffer = iterableDiffers.find([]).create(null);
    }

    public ngDoCheck(): void {
        // On the use of iterableDiffer, see https://stackoverflow.com/a/42962723/7611990.
        // Angular data-binding checking system does not check if the array has new elements, or
        // if it deleted them. When the user selects and unselect favorites, the array changes and
        // we must detect those changes to update the element selection on the tree.
        const changes = this.iterableDiffer.diff(this.favorites);
        if (changes) {
            this.updateSelection();
        }
    }

    public ngOnInit(): void {
        this.mainLanguageCode = this.translateService.getDefaultLang();
        this.categoryService.getTree().subscribe((categories) => {
            this.tree = categories;
            this.createTree();
        });
    }

    public createTree() {
        this.categoryListToTreeNode(this.tree).subscribe((treeNodes) => {
            this.resources = treeNodes;
        });
    }

    public updateSelection() {
        this.selectedResources = [];
        for (const node of this.nodeList) {
            this.unsetLoadingNode(node);
            if (this.isFavorite(node.data)) {
                this.selectedResources.push(node);
            }
        }
    }

    public onSelect(treeNode: TreeNode) {
        this.setLoadingNode(treeNode);
        this.onResourceSelect.emit(treeNode.data);
    }

    public onUnselect(treeNode: TreeNode) {
        this.setLoadingNode(treeNode);
        this.onResourceUnselect.emit(treeNode.data);
    }

    private categoryListToTreeNode(categories: Category[]): Observable<TreeNode[]> {
        return of(
            categories?.map((category) => {
                const children = [];

                // add category children to the tree
                this.categoryListToTreeNode(category.children).subscribe((treeNodes) => {
                    children.push(...treeNodes);
                });

                // add operations attached to the category
                this.operationService.find({ query: `CATEGORY_ID EQ ${category.id}` }).subscribe((operations) => {
                    children.push(...operations.map((operation) => this.operationToTreeNode(operation)));
                });

                return this.categoryToTreeNode(category, children);
            })
        );
    }

    private categoryToTreeNode(category: Category, children: TreeNode[]): TreeNode {
        const node = {
            label: category.name.getLocalisedLabel(this.mainLanguageCode),
            collapsedIcon: 'fa fa-folder',
            expandedIcon: 'fa fa-folder-open',
            expanded: true,
            children,
            data: category,
            selectable: !this.disabled,
            makingRequest: false,
        };

        if (this.isFavorite(category)) {
            this.selectedResources.push(node);
        }
        this.nodeList.push(node);

        return node;
    }

    private operationToTreeNode(operation: Operation): TreeNode {
        const node = {
            label: operation.name.getLocalisedLabel(this.mainLanguageCode),
            collapsedIcon: 'fa fa-table',
            expandedIcon: 'fa fa-table',
            expanded: true,
            data: operation,
            leaf: true,
            selectable: !this.disabled,
        };

        if (this.isFavorite(operation)) {
            this.selectedResources.push(node);
        }
        this.nodeList.push(node);

        return node;
    }

    private isFavorite(resource: Category | Operation): boolean {
        return this.favorites?.some((favorite) => favorite.resource.id === resource.id && favorite.resource.constructor === resource.constructor);
    }

    private setLoadingNode(treeNode: TreeNode) {
        treeNode.selectable = false;
        treeNode.icon = 'fa fa-spinner fa-spin';
    }

    private unsetLoadingNode(node: TreeNode) {
        node.icon = null;
        node.selectable = !this.disabled;
    }
}
